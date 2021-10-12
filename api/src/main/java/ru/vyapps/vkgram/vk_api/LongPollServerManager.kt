package ru.vyapps.vkgram.vk_api

import android.util.Log
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import ru.vyapps.vkgram.vk_api.data.LongPollServer
import java.io.IOException
import java.util.concurrent.TimeUnit

class LongPollServerManager(
    private val longPollServer: LongPollServer
) {

    private var ts = longPollServer.ts

    fun events() = flow {
        val timeout = Int.MAX_VALUE.toLong()
        val httpClient = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        while (true) {
            val url = "https://${longPollServer.server}?act=a_check&key=${longPollServer.key}&ts=$ts&wait=25&mode=2&version=3"
            val request = Request.Builder()
                .url(url).
                build()
            try {
                val response = httpClient.newCall(request).await()
                val responseBody = response.body ?: return@flow
                val longPollServerEvent = parse(responseBody) ?: return@flow
                emit(longPollServerEvent)

            } catch (e: IOException) {
                Log.e(TAG, e.stackTraceToString())
            }
        }
    }

    private fun parse(responseBody: ResponseBody): LongPollServerEvent? {
        val responseText = responseBody.string()
        val responseJsonObject = JSONObject(responseText)
        ts = responseJsonObject.getInt("ts")
        val updatesJsonArray = responseJsonObject.getJSONArray("updates")
        if (updatesJsonArray.length() < 1) return null

        val eventFlagCode = updatesJsonArray.getJSONArray(0).getInt(0)
        val eventFlag = getEventFlagByValue(eventFlagCode)
        eventFlag ?: return null
        val conversationId = updatesJsonArray.getJSONArray(0).getInt(3)
        return LongPollServerEvent(eventFlag, conversationId)
    }

    companion object {

        private const val TAG = "LongPollServerManager"

        private fun getEventFlagByValue(value: Int): EventFlag? {
            return EventFlag.values().firstOrNull { eventFlag ->
                eventFlag.code == value
            }
        }
    }
}

enum class EventFlag(
    val code: Int
    ) {

    SetFlag(2),
    NewMessage(4)
}

data class LongPollServerEvent (

    val eventFlag: EventFlag,
    val conversationId: Int
)

