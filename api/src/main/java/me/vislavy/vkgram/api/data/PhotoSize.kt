package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class PhotoSize(
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val type: String = ""
)
