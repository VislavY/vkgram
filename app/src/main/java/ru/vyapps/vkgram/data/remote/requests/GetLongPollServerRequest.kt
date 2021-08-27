package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.vyapps.vkgram.data.LongPollServer

class GetLongPollServerRequest : ApiCommand<LongPollServer?>() {

    override fun onExecute(manager: VKApiManager): LongPollServer? {
        val methodCall = VKMethodCall.Builder()
            .method("messages.getLongPollServer")
            .args("lp_version", 3)
            .version(manager.config.version)
            .build()
        return manager.execute(methodCall, Parser())
    }

    private class Parser: VKApiResponseParser<LongPollServer?> {

        override fun parse(response: String?): LongPollServer? {
            var longPollServer: LongPollServer? = null

            response?.let {
                val responseJObj = JSONObject(response).getJSONObject("response")
                longPollServer = Json.decodeFromString<LongPollServer>(responseJObj.toString())
            }

            return longPollServer
        }
    }
}