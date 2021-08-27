package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.vyapps.vkgram.data.Message

class GetMessageHistory(
    private val conversationId: Long,
    private val offset: Int
) : ApiCommand<List<Message>>() {

    override fun onExecute(manager: VKApiManager): List<Message> {
        val methodCall = VKMethodCall.Builder()
            .method("messages.getHistory")
            .args("user_id", conversationId)
            .args("offset", offset)
            .version(manager.config.version)
            .build()
        return manager.execute(methodCall, Parser())
    }

    private class Parser: VKApiResponseParser<List<Message>> {

        override fun parse(response: String?): List<Message> {
            val messages = ArrayList<Message>()

            response?.let {
                val messageJArray = JSONObject(it)
                    .getJSONObject("response")
                    .getJSONArray("items")
                for (i in 0 until messageJArray.length()) {
                    val messageJObj = messageJArray.get(i)
                    val json = Json { ignoreUnknownKeys = true }
                    val message = json.decodeFromString<Message>(messageJObj.toString())
                    messages.add(message)
                }
            }

            return messages
        }
    }
}