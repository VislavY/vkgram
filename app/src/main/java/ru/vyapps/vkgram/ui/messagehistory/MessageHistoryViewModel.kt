package ru.vyapps.vkgram.ui.messagehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.data.remote.Message
import ru.vyapps.vkgram.data.remote.User
import ru.vyapps.vkgram.data.repositories.MessageRepo
import ru.vyapps.vkgram.data.repositories.UserRepo

class MessageHistoryViewModel @AssistedInject constructor(
    @Assisted private val conversationId: Long,
    @Assisted private val token: String,
    private val userRepo: UserRepo,
    private val messageRepo: MessageRepo

) : ViewModel() {

    val loadedMessageCount = 20

    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    private val _loadedMessages = MutableSharedFlow<List<Message>>()
    val loadedMessages = _loadedMessages.asSharedFlow()

    init {
        getUserById(conversationId)
        getMessagesByConversationId(loadedMessageCount, 0)
    }

    fun getMessagesByConversationId(messageCount: Int, offset: Int) {
        viewModelScope.launch {
            val messages = messageRepo.getMessagesByConversationId(
                conversationId,
                messageCount,
                offset,
                token
            )
            _loadedMessages.emit(messages)
        }
    }

    private fun getUserById(userId: Long) {
        viewModelScope.launch {
            val user = userRepo.getUserById(userId, token)
            _user.emit(user)
        }
    }

    fun sendMessage(text: String) {
        if (text.isNotEmpty()) {
            viewModelScope.launch {
                messageRepo.sendMessage(
                    conversationId,
                    text,
                    token
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            conversationId: Long,
            token: String
        ): MessageHistoryViewModel
    }

    companion object {
        fun provideFactory(
            factory: Factory,
            conversationId: Long,
            token: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(conversationId, token) as T
            }
        }
    }
}