package ru.vyapps.vkgram.data.remote

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.remote.requests.GetConversationsRequest
import javax.inject.Inject

class ConversationsService @Inject constructor() {

    private val _conversations = MutableStateFlow<List<Conversation>>(ArrayList())
    val conversations = _conversations.asStateFlow()

    fun loadConversations(count: Int) {

        VK.execute(GetConversationsRequest(count), object: VKApiCallback<List<Conversation>> {

            override fun success(result: List<Conversation>) {
                _conversations.value = result
            }

            override fun fail(error: Exception) {

            }
        })
    }
}