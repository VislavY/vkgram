package me.vislavy.vkgram.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.EventHandler
import me.vislavy.vkgram.core.repositories.ConversationRepository
import me.vislavy.vkgram.core.repositories.FriendRepository
import me.vislavy.vkgram.core.repositories.UserRepository
import me.vislavy.vkgram.home.models.HomeEvent
import me.vislavy.vkgram.home.models.HomeViewState
import me.vislavy.vkgram.api.EventFlag
import me.vislavy.vkgram.api.LongPollServerManager
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val friendRepository: FriendRepository,
    private val longPollServerManager: LongPollServerManager,
    private val userRepository: UserRepository,
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
            is HomeEvent.EnterScreen -> enterScreen()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Error) {
        when (event) {
            is HomeEvent.ReloadScreen -> enterScreen(true)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Display) {
        when (event) {
            is HomeEvent.EnterScreen -> enterScreen()
            is HomeEvent.ConversationListEnd -> increaseConversationList(
                offset = event.count,
                currentState = currentState
            )
            is HomeEvent.FriendListEnd -> increaseFriendList(event.count, currentState)
            is HomeEvent.UpdateProfile -> updateProfile(currentState)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun enterScreen(needsToRefresh: Boolean = false) {
        if (needsToRefresh) {
            _viewState.value = HomeViewState.Loading
        }

        viewModelScope.launch {
            try {
                val profileResponse = userRepository.getUserListById(listOf(VK.getUserId()))[0]
                val conversationsResponse = conversationRepository.getConversationList(
                    count = DefaultConversationCount,
                    offset = 0
                )
                val friendsResponse = friendRepository.getFriendList(
                    count = DefaultFriendCount,
                    offset = 0
                )
                _viewState.value = HomeViewState.Display(
                    profile = profileResponse,
                    conversations =  conversationsResponse,
                    friends =  friendsResponse
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun increaseConversationList(offset: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val conversations = conversationRepository.getConversationList(
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
                val friends = friendRepository.getFriendList(
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
                val response = conversationRepository.getConversationList(
                    count = 1,
                    offset = 0
                ).first()
                println(response)
                val modifiedConversationList = currentState.conversations.toMutableList()
                modifiedConversationList.add(0, response)
                val outdatedConversations =
                    currentState.conversations.filter { it.properties.id == conversationId }
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
            if (conversationId != conversation.properties.id) return@lit

            val modifiedConversation = conversation.copy(onlineIndicatorEnabled = isOnline)
            val modifiedConversationList = currentState.conversations.toMutableList()
            modifiedConversationList[i] = modifiedConversation
            _viewState.value = currentState.copy(conversations = modifiedConversationList)

            return
        }
    }

    private fun updateProfile(currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserListById(listOf(VK.getUserId()))[0]
                _viewState.value = currentState.copy(profile = response)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    companion object {
        private const val Tag = "home"
        // Max - 100
        private const val DefaultConversationCount = 20
        // Max - 100
        private const val DefaultFriendCount = 20
    }
}