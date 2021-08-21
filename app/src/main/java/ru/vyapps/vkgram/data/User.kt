package ru.vyapps.vkgram.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val first_name: String,
    val last_name: String,
    val photo_200: String
)
