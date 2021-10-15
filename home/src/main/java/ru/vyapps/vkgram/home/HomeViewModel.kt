package ru.vyapps.vkgram.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.home.repositories.ConversationRepo
import ru.vyapps.vkgram.home.repositories.LongPollServerRepo
import ru.vyapps.vkgram.core.repositories.UserRepo
import ru.vyapps.vkgram.vk_api.EventFlag
import ru.vyapps.vkgram.vk_api.LongPollServerManager
import ru.vyapps.vkgram.vk_api.data.Friend

class HomeViewModel @AssistedInject constructor(
    @Assisted private val accessToken: String,
    private val conversationRepo: ConversationRepo,
    private val longPollServerRepo: LongPollServerRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations = _conversations.asStateFlow()

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends = _friends.asStateFlow()

    val user = flow {
        val receivedUser = userRepo.getUsersById(accessToken, VK.getUserId()).response
        emit(receivedUser.first())
    }

    init {
        getFriends()
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

    fun getFriends(offset: Int = 0) {
        viewModelScope.launch {
            val receivedFriends = userRepo.getFriends(
                accessToken = accessToken,
                count = FRIENDS_COUNT,
                offset = offset
            ).response.friends
            _friends.emit(friends.value + receivedFriends)
        }
    }

    fun addFriend(id: Int) {
        viewModelScope.launch {
            userRepo.addFriend(accessToken, id)
        }
    }

    fun deleteFriend(id: Int) {
        viewModelScope.launch {
            userRepo.deleteFriend(accessToken, id)
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
            _conversations.emit(conversationsCopy)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(accessToken: String): HomeViewModel
    }

    companion object {

        private const val CONVERSATION_COUNT = 20
        private const val FRIENDS_COUNT = 20

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