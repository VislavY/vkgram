package me.vislavy.vkgram.profile.models

sealed class ProfileEvent {
    data class Load(val uid: Long) : ProfileEvent()
    data class Reload(val uid: Long) : ProfileEvent()
    object BlockUser : ProfileEvent()
    object ClearAction : ProfileEvent()
    object FollowOrAddFriend : ProfileEvent()
    object UnfollowOrUnfriend : ProfileEvent()
    object IncreasePhotoList : ProfileEvent()
    object IncreaseVideoList : ProfileEvent()
    object IncreaseAudioList : ProfileEvent()
    object IncreaseFileList : ProfileEvent()
}