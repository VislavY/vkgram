package me.vislavy.vkgram.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.IntentHandler
import me.vislavy.vkgram.core.repositories.ConversationRepository
import me.vislavy.vkgram.core.repositories.FriendRepository
import me.vislavy.vkgram.core.repositories.UserRepository
import me.vislavy.vkgram.home.models.HomeIntent
import me.vislavy.vkgram.home.models.HomeViewState
import me.vislavy.vkgram.api.EventFlag
import me.vislavy.vkgram.api.LongPollServerManager
import me.vislavy.vkgram.core.ConversationModel
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val friendRepository: FriendRepository,
    private val longPollServerManager: LongPollServerManager,
    private val userRepository: UserRepository,
) : ViewModel(), IntentHandler<HomeIntent> {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            longPollServerManager.events().collect { event ->
                event?.apply {
                    val currentState = _viewState.value
                    if (currentState !is HomeViewState.Display) return@apply

                    when (eventFlag) {
                        EventFlag.NewMessage -> updateConversation(currentState = currentState)
                        EventFlag.FriendBecameOnline -> {
                            updateOnlineIndicator(
                                conversationId = abs(conversationId),
                                isOnline = true,
                                currentState = currentState
                            )
                        }
                        EventFlag.FriendBecameOffline -> {
                            updateOnlineIndicator(
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

    override fun onIntent(intent: HomeIntent) {
        when (val currentState = _viewState.value) {
            is HomeViewState.Loading -> reduce(intent, currentState)
            is HomeViewState.Error -> reduce(intent, currentState)
            is HomeViewState.Display -> reduce(intent, currentState)
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Loading) {
        when (intent) {
            is HomeIntent.EnterScreen -> enterScreen()
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Error) {
        when (intent) {
            is HomeIntent.ReloadScreen -> enterScreen(true)
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reduce(intent: HomeIntent, currentState: HomeViewState.Display) {
        when (intent) {
            is HomeIntent.EnterScreen -> enterScreen()
            is HomeIntent.IncreaseConvList -> increaseConversationList(
                offset = intent.currentSize,
                currentState = currentState
            )
            is HomeIntent.IncreaseFriendList -> increaseFriendList(intent.currentSize, currentState)
            is HomeIntent.UpdateProfile -> updateProfile(currentState)
            is HomeIntent.AddToSelectedConvList -> addConvToSelectedList(intent.conversation, currentState)
            is HomeIntent.ClearSelectedConvList -> clearSelectedConvList(currentState)
            is HomeIntent.DeleteSelectedConvs -> deleteSelectedConvs(currentState)
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun enterScreen(withLoadingScreen: Boolean = false) {
        if (withLoadingScreen) {
            _viewState.value = HomeViewState.Loading
        }

        viewModelScope.launch {
            try {
                val profile = userRepository.getUserListById(listOf(VK.getUserId()))[0]
                val conversations = conversationRepository.getConversationList(
                    count = DefaultConversationCount,
                    offset = 0
                )
                val friends = friendRepository.getFriendList(
                    count = DefaultFriendCount,
                    offset = 0
                )
                _viewState.value = HomeViewState.Display(
                    profile = profile,
                    conversations =  conversations,
                    friends =  friends
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

    private fun updateConversation(currentState: HomeViewState.Display) {
        viewModelScope.launch {
            try {
                val conversations = currentState.conversations.toMutableList()
                val updatedConversation = conversationRepository.getConversationList(1, 0)[0]
                conversations.remove(updatedConversation)
                conversations.add(0, updatedConversation)
                conversations.removeLast()
                _viewState.value = currentState.copy(conversations = conversations)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = HomeViewState.Error
            }
        }
    }

    private fun updateOnlineIndicator(
        conversationId: Int,
        isOnline: Boolean,
        currentState: HomeViewState.Display
    ) {
        currentState.conversations.forEachIndexed lit@{ index, conversation ->
            if (conversationId != conversation.properties.id) return@lit

            val modifiedConversation = conversation.copy(onlineIndicatorEnabled = isOnline)
            val modifiedConversationList = currentState.conversations.toMutableList()
            modifiedConversationList[index] = modifiedConversation
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

    private fun addConvToSelectedList(conversation: ConversationModel, currentState: HomeViewState.Display) {
        val selectedConversations = currentState.selectedConversations.toMutableList()
        if (selectedConversations.contains(conversation)) {
            selectedConversations.remove(conversation)
        } else {
            selectedConversations.add(conversation)
        }
        _viewState.value = currentState.copy(
            selectedConversations = selectedConversations,
            selectModeEnabled = selectedConversations.isNotEmpty()
        )
    }

    private fun clearSelectedConvList(currentState: HomeViewState.Display) {
        _viewState.value = currentState.copy(
            selectedConversations = emptyList(),
            selectModeEnabled = false
        )
    }

    private fun deleteSelectedConvs(currentState: HomeViewState.Display) {
        try {
            viewModelScope.launch {
                currentState.selectedConversations.forEach { conversation ->
                    conversationRepository.deleteConversation(conversation.properties.id)
                    delay(350)
                }

                val conversations = currentState.conversations.toMutableList()
                conversations.removeAll(currentState.selectedConversations)
                _viewState.value = currentState.copy(
                    conversations = conversations,
                    selectedConversations = emptyList(),
                    selectModeEnabled = false
                )
            }
        } catch (e: java.lang.Exception) {
            Log.e(Tag, e.toString())
            _viewState.value = HomeViewState.Error
        }
    }

    companion object {
        private const val Tag = "Home"

        // Max = 100
        private const val DefaultConversationCount = 20

        // Max = 100
        private const val DefaultFriendCount = 20
    }
}