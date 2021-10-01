package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("sizes") val properties: List<PhotoProperties>
)

@Serializable
data class PhotoProperties(
    val width: Int,
    val height: Int,
    val url: String,
)
