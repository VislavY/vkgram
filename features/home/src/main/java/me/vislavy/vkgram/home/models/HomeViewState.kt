package me.vislavy.vkgram.home.models

import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.api.data.User

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Error : HomeViewState()
    data class Display(
        val conversations: List<ConversationModel>,
        val friends: List<User>,
        val profile: User? = null
    ) : HomeViewState()
}