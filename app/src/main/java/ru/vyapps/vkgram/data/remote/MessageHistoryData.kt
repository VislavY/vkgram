package ru.vyapps.vkgram.data.remote

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

@Serializable
data class MessageHistoryResponse(
    val items: List<Message>
)

@Serializable
data class MessageHistoryData(
    val response: MessageHistoryResponse
)
