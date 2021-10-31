package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val sizes: List<PhotoProperties>
)

@Serializable
data class PhotoProperties(
    val width: Int,
    val height: Int,
    val url: String,
)
