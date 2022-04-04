package me.vislavy.vkgram.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.base.DisplayState
import me.vislavy.vkgram.core.base.MviViewModel
import me.vislavy.vkgram.core.repositories.FriendRepository
import me.vislavy.vkgram.core.repositories.UserRepository
import me.vislavy.vkgram.profile.models.ProfileAction
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
) : ViewModel(), MviViewModel<ProfileEvent> {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _action = MutableStateFlow<ProfileAction?>(null)
    val action = _action.asStateFlow()

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Load -> load(event.uid)
            is ProfileEvent.Reload -> load(event.uid, true)
            is ProfileEvent.SubscribeOrAddFriend -> subscribeOrAddFriend()
            is ProfileEvent.UnsubscribeOrUnfriend -> unsubscribeUserOrUnfriend()
        }
    }

    private fun load(id: Int, isReload: Boolean = false) {
        viewModelScope.launch {
            try {
                if (isReload) {
                    _state.value = state.value.copy(displayState = DisplayState.Reloading)
                }

                val user = userRepository.getUserListById(listOf(id)).first()
                val isYourProfile = user.id == VK.getUserId()
                _state.value = ProfileState(DisplayState.Display, user, isYourProfile)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _action.value = ProfileAction.ShowError
            }
        }
    }

    private fun subscribeOrAddFriend() {
        viewModelScope.launch {
            try {
                val user = state.value.user ?: return@launch
                friendRepository.addFriend(user.id)
                val friendStatus = friendRepository.getFriendsStatus(listOf(user.id)).first()
                _state.value = state.value.copy(
                    user = user.copy(friendStatus = friendStatus.status)
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _action.value = ProfileAction.ShowError
            }
        }
    }

    private fun unsubscribeUserOrUnfriend() {
        viewModelScope.launch {
            try {
                val user = state.value.user ?: return@launch
                friendRepository.deleteFriend(user.id)
                val friendStatus = friendRepository.getFriendsStatus(listOf(user.id)).first()
                _state.value = state.value.copy(
                    user = user.copy(friendStatus = friendStatus.status)
                )
            } catch (e: java.lang.Exception) {
                Log.e(Tag, e.toString())
                _action.value = ProfileAction.ShowError
            }
        }
    }

    companion object {
        private const val Tag = "Profile"

        // Max = 200
        private const val DefaultAttachmentCount = 200
    }
}