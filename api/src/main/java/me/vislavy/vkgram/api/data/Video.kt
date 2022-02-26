package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val image: List<PhotoSize> = emptyList(),
    @SerialName("first_frame") val firstFrame: List<PhotoSize> = emptyList(),
    @SerialName("player") val playerUrl: String = ""
)
