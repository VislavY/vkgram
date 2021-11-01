package ru.vyapps.vkgram.home.models

import ru.vyapps.vkgram.core.Conversation
import ru.vyapps.vkgram.vk_api.data.User

sealed class HomeViewState {
    object Loading : HomeViewState()
    object Error : HomeViewState()
    data class Display(
        val conversations: List<Conversation>,
        val friends: List<User>
    ) : HomeViewState()
}