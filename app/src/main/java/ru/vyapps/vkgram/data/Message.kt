package ru.vyapps.vkgram.data

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long,
    val from_id: Long,
    val peer_id: Long,
    val text: String,
    val date: Long,
    val out: Int,
)
