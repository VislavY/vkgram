package me.vislavy.vkgram.profile.models

import me.vislavy.vkgram.api.data.User

sealed class ProfileViewState {
    object Loading : ProfileViewState()
    object Error : ProfileViewState()
    data class Display(val user: User) : ProfileViewState()
}
