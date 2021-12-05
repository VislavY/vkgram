package ru.vyapps.vkgram.profile.models

import ru.vyapps.vkgram.vk_api.data.ProfileInfo
import ru.vyapps.vkgram.vk_api.data.User

sealed class ProfileViewState {
    object Loading : ProfileViewState()
    object Error : ProfileViewState()
    data class Display(val user: User) : ProfileViewState()
}
