package me.vislavy.vkgram.new_conversation.screens.members_choice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.repositories.FriendRepository
import me.vislavy.vkgram.core.EventHandler
import me.vislavy.vkgram.new_conversation.UserModel
import me.vislavy.vkgram.new_conversation.screens.members_choice.models.MembersChoiceEvent
import me.vislavy.vkgram.new_conversation.screens.members_choice.models.MembersChoiceViewState
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MembersChoiceViewModel @Inject constructor(
    private val friendRepository: FriendRepository
) : ViewModel(), EventHandler<MembersChoiceEvent> {

    private val _viewState = MutableStateFlow<MembersChoiceViewState>(MembersChoiceViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: MembersChoiceEvent) {
        when (val currentState = _viewState.value) {
            is MembersChoiceViewState.Loading -> reduce(event, currentState)
            is MembersChoiceViewState.Error -> reduce(event, currentState)
            is MembersChoiceViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(event: MembersChoiceEvent, currentState: MembersChoiceViewState.Loading) {
        when (event) {
            is MembersChoiceEvent.EnterScreen -> fetchFriendList()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: MembersChoiceEvent, currentState: MembersChoiceViewState.Error) {
        when (event) {
            MembersChoiceEvent.ReloadScreen -> fetchFriendList(true)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: MembersChoiceEvent, currentState: MembersChoiceViewState.Display) {
        when (event) {
            is MembersChoiceEvent.EnterScreen -> fetchFriendList()
            is MembersChoiceEvent.OnFriendClick -> performFriendClick(event.user, currentState)
            is MembersChoiceEvent.OnItemListEnd -> fetchFriendList(offset = event.itemCount)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun performFriendClick(member: UserModel, currentState: MembersChoiceViewState.Display) {
        val newMembers = currentState.members.toMutableList()

        if (member.isSelected)
            newMembers.add(member) else newMembers.remove(member)

        val fabVisible = newMembers.isNotEmpty()
        _viewState.value = currentState.copy(
            members = newMembers,
            fabVisible = fabVisible
        )
    }

    private fun fetchFriendList(needsToRefresh: Boolean = false, offset: Int = 0) {
        if (needsToRefresh) {
            _viewState.value = MembersChoiceViewState.Loading
        }

        viewModelScope.launch {
            try {
                val response = friendRepository.getFriendList(
                    count = DEFAULT_FRIEND_COUNT,
                    offset = offset
                )
                val items = response.map { user ->
                    UserModel(
                        id = user.id,
                        domain = user.domain,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        photo = user.photo
                    )
                }

                if (_viewState.value is MembersChoiceViewState.Display) {
                    val currentState = (_viewState.value as MembersChoiceViewState.Display)
                    val newItems = currentState.items.toMutableList()
                    newItems.addAll(items)
                    _viewState.value = currentState.copy(items = newItems)
                } else {
                    _viewState.value = MembersChoiceViewState.Display(items, emptyList())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                _viewState.value = MembersChoiceViewState.Error
            }
        }
    }

    companion object {
        private const val TAG = "newConversation"
        private const val DEFAULT_FRIEND_COUNT = 20
    }
}