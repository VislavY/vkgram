package me.vislavy.vkgram.home.models

import me.vislavy.vkgram.core.ConversationModel

sealed class HomeIntent {
    object EnterScreen : HomeIntent()
    object ReloadScreen : HomeIntent()
    data class IncreaseConvList(val currentSize: Int) : HomeIntent()
    data class IncreaseFriendList(val currentSize: Int) : HomeIntent()
    object UpdateProfile : HomeIntent()
    data class AddToSelectedConvList(val conversation: ConversationModel) : HomeIntent()
    object ClearSelectedConvList : HomeIntent()
    object DeleteSelectedConvs : HomeIntent()
}