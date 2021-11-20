package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val title: String
)
