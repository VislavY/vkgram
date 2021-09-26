package ru.vyapps.vkgram.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.conversations.repositories.ConversationRepo

class ConversationsViewModel @AssistedInject constructor(
    @Assisted private val accessToken: String,
    private val conversationRepo: ConversationRepo
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations = _conversations.asStateFlow()

    init {
        getConversations()
    }

    fun getConversations(offset: Int = 0) {
        viewModelScope.launch {
            val receivedConversations = conversationRepo.getConversations(
                defaultConversationCount,
                offset,
                accessToken
            )
           _conversations.value = (conversations.value + receivedConversations)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(accessToken: String): ConversationsViewModel
    }

    companion object {

        private const val defaultConversationCount = 20

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