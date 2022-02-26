package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    val response: Photo
)

@Serializable
data class Photo(
    val id: Int = 0,
    @SerialName("owner_id") val ownerId: Int = 0,
    val sizes: List<PhotoSize> = emptyList()
)