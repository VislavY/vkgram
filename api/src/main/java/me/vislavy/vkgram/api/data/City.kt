package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val title: String
)
