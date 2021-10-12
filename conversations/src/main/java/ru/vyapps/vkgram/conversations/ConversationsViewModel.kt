package ru.vyapps.vkgram.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.conversations.repositories.ConversationRepo
import ru.vyapps.vkgram.conversations.repositories.LongPollServerRepo
import ru.vyapps.vkgram.vk_api.EventFlag
import ru.vyapps.vkgram.vk_api.LongPollServerManager

class ConversationsViewModel @AssistedInject constructor(
    @Assisted private val accessToken: String,
    private val conversationRepo: ConversationRepo,
    private val longPollServerRepo: LongPollServerRepo
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations = _conversations.asStateFlow()

    init {
        getConversations()

        viewModelScope.launch {
            val longPollServer = longPollServerRepo.getLongPollServer(accessToken)
            val longPollServerManager = LongPollServerManager(longPollServer.response)
            longPollServerManager.events().collect { event ->
                if (event.eventFlag == EventFlag.NewMessage) {
                    updateConversation(event.conversationId)
                }
            }
        }
    }

    fun getConversations(offset: Int = 0) {
        viewModelScope.launch {
            val receivedConversations = conversationRepo.getConversations(
                accessToken = accessToken,
                count = CONVERSATION_COUNT,
                offset = offset,
            )
           _conversations.value = (conversations.value + receivedConversations)
        }
    }

    private fun updateConversation(id: Int) {
        viewModelScope.launch {
            val updatedConversation = conversationRepo.getConversations(
                accessToken = accessToken,
                count = 1,
                offset = 0
            ).first()

            val conversationsCopy = conversations.value.toMutableList()
            for (conversation in conversations.value) {
                if (conversation.id == id) {
                    conversationsCopy.remove(conversation)
                    break
                }
            }

            conversationsCopy.add(0, updatedConversation)
            _conversations.compareAndSet(conversations.value, conversationsCopy)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(accessToken: String): ConversationsViewModel
    }

    companion object {

        private const val CONVERSATION_COUNT = 20

        fun provideFactory(
            factory: Factory,
            accessToken: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(accessToken) as T
            }
        }
    }
}