package me.vislavy.vkgram.home.models

import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.api.data.User

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Error : HomeViewState()
    data class Display(
        val conversations: List<ConversationModel> = emptyList(),
        val friends: List<User> = emptyList(),
        val profile: User? = null,
        val selectModeEnabled: Boolean = false,
        val selectedConversations: List<ConversationModel> = emptyList()
    ) : HomeViewState()
}