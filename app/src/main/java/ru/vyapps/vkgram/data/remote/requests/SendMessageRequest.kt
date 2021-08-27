package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand

class SendMessageRequest(
    private val conversationId: Long,
    private val message: String
) : ApiCommand<Unit>() {

    override fun onExecute(manager: VKApiManager) {
        val methodCall = VKMethodCall.Builder()
            .method("messages.send")
            .args("peer_id", conversationId)
            .args("random_id", 0)
            .args("message", message)
            .version(manager.config.version)
            .build()
        manager.execute(methodCall)
    }
}