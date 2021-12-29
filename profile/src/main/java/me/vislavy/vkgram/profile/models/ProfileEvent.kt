package me.vislavy.vkgram.profile.models

sealed class ProfileEvent {
    data class EnterScreen(val userId: Int) : ProfileEvent()
    data class Reload(val userId: Int) : ProfileEvent()
}
