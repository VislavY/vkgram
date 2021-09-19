package ru.vyapps.vkgram.ui.conversationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.repositories.MessageRepo

class ConversationListViewModel @AssistedInject constructor(
    @Assisted private val token: String,
    private val messageRepo: MessageRepo,
) : ViewModel() {

    val loadedConversationsCount = 20

    private val _loadedConversations = MutableSharedFlow<List<Conversation>>()
    val loadedConversations = _loadedConversations.asSharedFlow()

    init {
        getConversations(loadedConversationsCount, 0)

        viewModelScope.launch {
//            LongPollServerClient.result.collect { result ->
//                if (result is NewMessageEvent) {
//
//                }
//            }
        }

    }

    fun getConversations(count: Int, offset: Int) {
        viewModelScope.launch {
            val conversations = messageRepo.getConversations(count, offset, token)
            _loadedConversations.emit(conversations)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(token: String): ConversationListViewModel
    }

    companion object {
        fun provideFactory(
            factory: Factory,
            token: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(token) as T
            }
        }
    }
}