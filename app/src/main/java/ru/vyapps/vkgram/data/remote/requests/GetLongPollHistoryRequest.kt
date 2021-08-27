package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.vyapps.vkgram.data.Message

class GetLongPollHistoryRequest(
    private val ts: Long
) : ApiCommand<Message?>() {

    override fun onExecute(manager: VKApiManager): Message? {
        val methodCall = VKMethodCall.Builder()
            .method("messages.getLongPollHistory")
            .args("ts", ts)
            .version(manager.config.version)
            .build()
        return manager.execute(methodCall, Parser())
    }

    private class Parser: VKApiResponseParser<Message?> {

        override fun parse(response: String?): Message? {
            var message: Message? = null

            response?.let {
                val messagesJArray = JSONObject(response)
                    .getJSONObject("response")
                    .getJSONObject("messages")
                    .getJSONArray("items")
                if (messagesJArray.length() > 0) {
                    val messageJObj = messagesJArray.get(0)
                    val json = Json { ignoreUnknownKeys = true }
                    message = json.decodeFromString<Message>(messageJObj.toString())
                }
            }

            return message
        }
    }
}