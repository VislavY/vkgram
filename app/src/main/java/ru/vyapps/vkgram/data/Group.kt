package ru.vyapps.vkgram.data

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Long,
    val name: String,
    val is_closed: Int,
    val type: String,
    val photo_200: String
)
