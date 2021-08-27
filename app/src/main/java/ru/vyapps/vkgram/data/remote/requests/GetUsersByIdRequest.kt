package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.vyapps.vkgram.data.User

class GetUsersByIdRequest(
    private val userId: Long
) : ApiCommand<User?>() {

    override fun onExecute(manager: VKApiManager): User? {
        val methodCall = VKMethodCall.Builder()
            .method("users.get")
            .args("lang", "ru")
            .args("user_ids", userId)
            .args("fields", "photo_50,photo_100,photo_200")
            .version(manager.config.version)
            .build()
        return manager.execute(methodCall, Parser())
    }

    private class Parser : VKApiResponseParser<User?> {

        override fun parse(response: String?): User? {
            var user: User? = null

            response?.let {
                val userJArray = JSONObject(response).getJSONArray("response")
                for (i in 0 until userJArray.length()) {
                    val userJObj = userJArray.get(i)
                    val json = Json { ignoreUnknownKeys = true }
                    user = json.decodeFromString<User>(userJObj.toString())
                }
            }

            return user
        }
    }
}