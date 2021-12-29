package ru.vyapps.vkgram.home.models

import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.vk_api.data.User

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Error : HomeViewState()
    data class Display(
        val conversations: List<ConversationModel>,
        val friends: List<User>,
        val profile: User? = null
    ) : HomeViewState()
}