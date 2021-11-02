package ru.vyapps.vkgram.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.EventHandler
import ru.vyapps.vkgram.vk_api.VkAccessToken
import ru.vyapps.vkgram.core.repositories.ConversationRepo
import ru.vyapps.vkgram.core.repositories.FriendRepository
import ru.vyapps.vkgram.home.models.HomeEvent
import ru.vyapps.vkgram.home.models.HomeViewState
import ru.vyapps.vkgram.vk_api.EventFlag
import ru.vyapps.vkgram.vk_api.LongPollServerManager
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vkAccessToken: VkAccessToken,
    private val conversationRepo: ConversationRepo,
    private val friendRepository: FriendRepository,
    private val longPollServerManager: LongPollServerManager
) : ViewModel(), EventHandler<HomeEvent> {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            longPollServerManager.events().collect { event ->
                event?.apply {
                    val currentState = _viewState.value
                    if (currentState !is HomeViewState.Display) return@apply

                    when (eventFlag) {
                        EventFlag.NewMessage -> updateConversation(
                            conversationId = conversationId,
                            currentState = currentState
                        )
                        EventFlag.FriendBecameOnline -> {
                            changeOnlineIndicator(
                                conversationId = abs(conversationId),
                                isOnline = true,
                                currentState = currentState
                            )
                        }
                        EventFlag.FriendBecameOffline -> {
                            changeOnlineIndicator(
                                conversationId = abs(conversationId),
                                isOnline = false,
                                currentState = currentState
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (val currentState = _viewState.value) {
            is HomeViewState.Loading -> reduce(event, currentState)
            is HomeViewState.Error -> reduce(event, currentState)
            is HomeViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Loading) {
        when (event) {
            is HomeEvent.EnterScreen -> performScreenEnter()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Error) {
        when (event) {
            is HomeEvent.ReloadScreen -> performScreenEnter(true)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Display) {
        when (event) {
            is HomeEvent.EnterScreen -> performScreenEnter()
            is HomeEvent.ConversationListEnd -> increaseConversationList(
                offset = event.count,
                currentState = currentState
            )
            is HomeEvent.FriendListEnd -> increaseFriendList(event.count, currentState)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun performScreenEnter(needsToRefresh: Boolean = false) {
        if (needsToRefresh) {
            _viewState.value = HomeViewState.Loading
        }

        viewModelScope.launch {
            try {
                val conversationsResponse = conversationRepo.getConversations(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultConversationCount,
                    offset = 0
                )
                val friendsResponse = friendRepository.fetchFriendList(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultFriendCount,
                    offset = 0
                )
                _viewState.value = HomeViewState.Display(conversationsResponse, friendsResponse)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun increaseConversationList(offset: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val conversations = conversationRepo.getConversations(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultConversationCount,
                    offset = offset
                )
                val modifiedConversationList = currentState.conversations.toMutableList()
                modifiedConversationList.addAll(conversations)
                _viewState.value = currentState.copy(conversations = modifiedConversationList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun increaseFriendList(offset: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val friends = friendRepository.fetchFriendList(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultFriendCount,
                    offset = offset
                )
                val modifiedFriendList = currentState.friends.toMutableList()
                modifiedFriendList.addAll(friends)
                _viewState.value = currentState.copy(friends = modifiedFriendList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun updateConversation(conversationId: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val response = conversationRepo.getConversations(
                    accessToken = vkAccessToken.accessToken,
                    count = 1,
                    offset = 0
                ).first()
                println(response)
                val modifiedConversationList = currentState.conversations.toMutableList()
                modifiedConversationList.add(0, response)
                val outdatedConversations =
                    currentState.conversations.filter { it.id == conversationId }
                modifiedConversationList.removeAll(outdatedConversations)
                _viewState.value = currentState.copy(conversations = modifiedConversationList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun changeOnlineIndicator(
        conversationId: Int,
        isOnline: Boolean,
        currentState: HomeViewState.Display
    ) {
        currentState.conversations.forEachIndexed lit@{ i, conversation ->
            if (conversationId != conversation.id) return@lit

            val modifiedConversation = conversation.copy(
                user = conversation.user?.copy(
                    online = if (isOnline) {
                        1
                    } else {
                        0
                    }
                )
            )

            val modifiedConversationList = currentState.conversations.toMutableList()
            modifiedConversationList[i] = modifiedConversation
            _viewState.value = currentState.copy(conversations = modifiedConversationList)

            return
        }
    }

    companion object {
        private const val Tag = "home"
        private const val DefaultConversationCount = 20
        private const val DefaultFriendCount = 20
    }
}