package ru.vyapps.vkgram.message_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.message_history.repositories.MessageRepo

class MessageHistoryViewModel @AssistedInject constructor(
    @Assisted private val conversationId: Long,
    @Assisted private val accessToken: String,
    private val messageRepo: MessageRepo
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    init {
        getMessages()
    }

    fun getMessages(offset: Int = 0) {
        viewModelScope.launch {
            val receivedMessages = messageRepo.getMessages(
                conversationId,
                defaultMessageCount,
                offset,
                accessToken
            )
            _messages.value = (messages.value + receivedMessages)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            conversationId: Long,
            accessToken: String
        ): MessageHistoryViewModel
    }

    companion object {

        private const val defaultMessageCount = 20

        fun provideFactory(
            factory: Factory,
            conversationId: Long,
            accessToken: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(conversationId, accessToken) as T
            }
        }
    }
}