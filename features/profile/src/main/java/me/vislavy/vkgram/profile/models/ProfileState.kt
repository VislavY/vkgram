package me.vislavy.vkgram.profile.models

import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.base.DisplayState

data class ProfileState (
    val displayState: DisplayState = DisplayState.Loading,
    val user: User? = null,
    val isYourProfile: Boolean = false,
    val isAttachmentsVisible: Boolean = false,
    val photos: List<Photo> = emptyList()
)
