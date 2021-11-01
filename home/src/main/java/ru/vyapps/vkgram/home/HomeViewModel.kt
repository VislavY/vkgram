package ru.vyapps.vkgram.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.EventHandler
import ru.vyapps.vkgram.core.VkAccessToken
import ru.vyapps.vkgram.core.repositories.ConversationRepo
import ru.vyapps.vkgram.core.repositories.FriendRepository
import ru.vyapps.vkgram.home.models.HomeEvent
import ru.vyapps.vkgram.home.models.HomeViewState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vkAccessToken: VkAccessToken,
    private val conversationRepo: ConversationRepo,
    private val friendRepository: FriendRepository
) : ViewModel(), EventHandler<HomeEvent> {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: HomeEvent) {
        when (val currentState = _viewState.value) {
            is HomeViewState.Loading -> reduce(event, currentState)
            is HomeViewState.Error -> reduce(event, currentState)
            is HomeViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Loading) {
        when (event) {
            is HomeEvent.EnterScreen -> performEnterScreen()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Error) {
        when (event) {
            is HomeEvent.ReloadScreen -> performEnterScreen(true)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: HomeEvent, currentState: HomeViewState.Display) {
        when (event) {
            is HomeEvent.EnterScreen -> performEnterScreen()
            is HomeEvent.ConversationListEnd -> performExpandConversationList(event.count, currentState)
            is HomeEvent.FriendListEnd -> performExpandFriendList(event.count, currentState)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun performEnterScreen(needsToRefresh: Boolean = false) {
        if (needsToRefresh) {
            _viewState.value = HomeViewState.Loading
        }

        viewModelScope.launch {
            try {
                val conversations = conversationRepo.getConversations(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultConversationCount,
                    offset = 0
                )
                val friends = friendRepository.fetchFriendList(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultFriendCount,
                    offset = 0
                )
                _viewState.value = HomeViewState.Display(conversations, friends)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun performExpandConversationList(offset: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val conversations = conversationRepo.getConversations(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultConversationCount,
                    offset = offset
                )
                val newConversationList = currentState.conversations.toMutableList()
                newConversationList.addAll(conversations)
                _viewState.value = currentState.copy(conversations = newConversationList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun performExpandFriendList(offset: Int, currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val friends = friendRepository.fetchFriendList(
                    accessToken = vkAccessToken.accessToken,
                    count = DefaultFriendCount,
                    offset = offset
                )
                val newFriendList = currentState.friends.toMutableList()
                newFriendList.addAll(friends)
                _viewState.value = currentState.copy(friends = newFriendList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    companion object {
        private const val Tag = "home"
        private const val DefaultConversationCount = 20
        private const val DefaultFriendCount = 20
    }
}