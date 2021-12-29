package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class County(
    val id: Int,
    val title: String
)
