package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val sizes: List<PhotoSize>
)