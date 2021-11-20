package ru.vyapps.vkgram.profile.models

sealed class ProfileEvent {
    object EnterScreen : ProfileEvent()
    object Reload : ProfileEvent()
}
