package me.vislavy.vkgram.profile.models

sealed class ProfileEvent {
    data class Load(val uid: Int) : ProfileEvent()
    data class Reload(val uid: Int) : ProfileEvent()

    object SubscribeOrAddFriend : ProfileEvent()
    object UnsubscribeOrUnfriend : ProfileEvent()
}