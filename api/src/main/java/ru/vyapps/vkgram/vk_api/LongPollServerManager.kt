package ru.vyapps.vkgram.vk_api

import android.util.Log
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import ru.vyapps.vkgram.vk_api.data.LongPollServer
import java.util.concurrent.TimeUnit


class LongPollServerManager(vkAccessToken: VkAccessToken, vkService: VkService) {

    private var httpClient: OkHttpClient
    private var longPollServer: LongPollServer
    private var ts: Int = 0

    init {
        val timeout = Int.MAX_VALUE.toLong()
        httpClient = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        runBlocking {
            longPollServer = vkService.getLongPollServer(vkAccessToken.accessToken).response
            ts = longPollServer.ts
        }
    }

    fun events() = flow {
        while (true) {
            val url =
                "https://${longPollServer.server}?act=a_check&key=${longPollServer.key}&ts=$ts&wait=25&mode=2&version=3"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response = httpClient.newCall(request).await()
                val responseBody = response.body ?: return@flow
                val longPollServerEvent = parse(responseBody)
                emit(longPollServerEvent)
            } catch (e: Exception) {
                Log.e(Tag, e.stackTraceToString())
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

        val conversationId = when (eventFlag) {
            EventFlag.NewMessage -> updatesJsonArray.getJSONArray(0).getInt(3)
            EventFlag.FriendBecameOnline -> updatesJsonArray.getJSONArray(0).getInt(1)
            EventFlag.FriendBecameOffline -> updatesJsonArray.getJSONArray(0).getInt(1)
        }

        return LongPollServerEvent(eventFlag, conversationId)
    }

    companion object {
        private const val Tag = "LongPollServerManager"

        private fun getEventFlagByValue(value: Int): EventFlag? {
            return EventFlag.values().firstOrNull { eventFlag ->
                eventFlag.code == value
            }
        }
    }
}

enum class EventFlag(val code: Int) {
    NewMessage(4),
    FriendBecameOnline(8),
    FriendBecameOffline(9)
}

data class LongPollServerEvent(
    val eventFlag: EventFlag,
    val conversationId: Int
)

