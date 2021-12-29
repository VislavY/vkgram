package ru.vyapps.vkgram.home.models

sealed class HomeEvent {
    object EnterScreen : HomeEvent()
    object ReloadScreen : HomeEvent()
    data class ConversationListEnd(val count: Int) : HomeEvent()
    data class FriendListEnd(val count: Int) : HomeEvent()
    object UpdateProfile : HomeEvent()
}