package me.vislavy.vkgram.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import com.vk.sdk.api.messages.dto.MessagesGetHistoryAttachmentsMediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.base.DisplayState
import me.vislavy.vkgram.core.base.MviViewModel
import me.vislavy.vkgram.core.repositories.DialogRepository
import me.vislavy.vkgram.core.repositories.FriendRepository
import me.vislavy.vkgram.core.repositories.UserRepository
import me.vislavy.vkgram.profile.models.ProfileAction
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dialogRepository: DialogRepository,
    private val friendRepository: FriendRepository,
) : MviViewModel<ProfileState, ProfileEvent, ProfileAction>(ProfileState()) {

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.Load -> load(event.uid)
            is ProfileEvent.Reload -> load(event.uid, true)
            is ProfileEvent.ClearAction -> mutableAction == null
            is ProfileEvent.IncreasePhotoList -> increasePhotoList()
            is ProfileEvent.IncreaseVideoList -> increaseVideoList()
            is ProfileEvent.IncreaseAudioList -> increaseAudioList()
            is ProfileEvent.IncreaseFileList -> increaseFileList()
            is ProfileEvent.FollowOrAddFriend -> followOrAddFriend()
            is ProfileEvent.UnfollowOrUnfriend -> unfollowOrUnfriend()
        }
    }

    private fun load(uid: Long, isReload: Boolean = false) {
        viewModelScope.launch {
            try {
                if (isReload) {
                    mutableState = mutableState.copy(displayState = DisplayState.Reloading)
                    delay(1000)
                }

                val user = userRepository.fetchUser(uid).copy(
                    isYourProfile = VK.getUserId().value == uid
                )
                val photoAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = uid,
                    type = MessagesGetHistoryAttachmentsMediaType.PHOTO,
                    count = DefaultPhotoCount,
                )
                val videoAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = uid,
                    type = MessagesGetHistoryAttachmentsMediaType.VIDEO,
                    count = DefaultVideoCount
                )
                val audioAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = uid,
                    type = MessagesGetHistoryAttachmentsMediaType.AUDIO,
                    count = DefaultAudioCount
                )
                val fileAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = uid,
                    type = MessagesGetHistoryAttachmentsMediaType.DOC,
                    count = DefaultFileCount
                )
                mutableState = ProfileState(
                    displayState = DisplayState.Display,
                    user = user,
                    isYourProfile = user.id == VK.getUserId().value,
                    photoAttachments = photoAttachments,
                    photoAttachmentListEnd = photoAttachments.isEmpty(),
                    videoAttachments = videoAttachments,
                    videoAttachmentListEnd = videoAttachments.isEmpty(),
                    audioAttachments = audioAttachments,
                    audioAttachmentListEnt = audioAttachments.isEmpty(),
                    fileAttachments = fileAttachments,
                    fileAttachmentListEnd = fileAttachments.isEmpty()
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())

                val user = userRepository.fetchLocalUser(uid)
                user?.let {
                    val isYourProfile = user.id == VK.getUserId().value
                    mutableState = ProfileState(DisplayState.Display, user, isYourProfile)
                    mutableAction = ProfileAction.ShowError
                    return@launch
                }

                mutableState = ProfileState(DisplayState.Error)
            }
        }
    }

    private fun increasePhotoList() {
        if (mutableState.photoAttachmentListEnd) return

        viewModelScope.launch {
            try {
                val currentPhotoAttachments = mutableState.photoAttachments.toMutableList()
                val photoAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = mutableState.user!!.id,
                    type = MessagesGetHistoryAttachmentsMediaType.PHOTO,
                    count = DefaultPhotoCount,
                    offset = currentPhotoAttachments.last().messageId - 1
                )
                currentPhotoAttachments.addAll(photoAttachments)
                mutableState = mutableState.copy(
                    photoAttachments = currentPhotoAttachments,
                    photoAttachmentListEnd = photoAttachments.isEmpty()
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    private fun increaseVideoList() {
        if (mutableState.videoAttachmentListEnd) return

        viewModelScope.launch {
            try {
                val currentVideoAttachments = mutableState.videoAttachments.toMutableList()
                val videoAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = mutableState.user!!.id,
                    type = MessagesGetHistoryAttachmentsMediaType.VIDEO,
                    count = DefaultVideoCount,
                    offset = currentVideoAttachments.last().messageId - 1
                )
                currentVideoAttachments.addAll(videoAttachments)
                mutableState = mutableState.copy(
                    videoAttachments = currentVideoAttachments,
                    videoAttachmentListEnd = videoAttachments.isEmpty()
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    private fun increaseAudioList() {
        if (mutableState.audioAttachmentListEnt) return

        viewModelScope.launch {
            try {
                val currentAudioAttachments = mutableState.audioAttachments.toMutableList()
                val audioAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = mutableState.user!!.id,
                    type = MessagesGetHistoryAttachmentsMediaType.AUDIO,
                    count = DefaultAudioCount,
                    offset = currentAudioAttachments.last().messageId - 1
                )
                currentAudioAttachments.addAll(audioAttachments)
                mutableState = mutableState.copy(
                    audioAttachments = currentAudioAttachments,
                    audioAttachmentListEnt = audioAttachments.isEmpty()
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    private fun increaseFileList() {
        if (mutableState.fileAttachmentListEnd) return

        viewModelScope.launch {
            try {
                val currentFileAttachments = mutableState.fileAttachments.toMutableList()
                val fileAttachments = dialogRepository.fetchDialogAttachments(
                    dialogId = mutableState.user!!.id,
                    type = MessagesGetHistoryAttachmentsMediaType.DOC,
                    count = DefaultFileCount,
                    offset = currentFileAttachments.last().messageId - 1
                )
                currentFileAttachments.addAll(fileAttachments)
                mutableState = mutableState.copy(
                    fileAttachments = currentFileAttachments,
                    fileAttachmentListEnd = fileAttachments.isEmpty()
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    private fun followOrAddFriend() {
        viewModelScope.launch {
            try {
                val user = mutableState.user!!
                friendRepository.followOrAddFriend(user.id)
                val newFriendStatus = friendRepository.fetchFriendStatus(user.id)
                mutableState = mutableState.copy(
                    user = user.copy(friendStatus = newFriendStatus)
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    private fun unfollowOrUnfriend() {
        viewModelScope.launch {
            try {
                val user = mutableState.user!!
                friendRepository.unfollowOrUnfriend(user.id)
                val newFriendStatus = friendRepository.fetchFriendStatus(user.id)
                mutableState = mutableState.copy(
                    user = user.copy(friendStatus = newFriendStatus)
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                mutableAction = ProfileAction.ShowError
            }
        }
    }

    companion object {
        private const val Tag = "Profile"
        private const val DefaultPhotoCount = 21
        private const val DefaultVideoCount = 21
        private const val DefaultAudioCount = 20
        private const val DefaultFileCount = 20
    }
}