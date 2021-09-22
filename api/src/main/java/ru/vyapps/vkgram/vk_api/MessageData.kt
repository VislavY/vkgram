package ru.vyapps.vkgram.vk_api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Message(
    val id: Long,
    @SerialName("from_id") val userId: Long,
    @SerialName("peer_id") val ConversationId: Long,
    val text: String,
    @Serializable(DateSerializer::class) val date: Date,
    val out: Int,
)

@Serializable
data class MessageResponse(
    val items: List<Message>
)

@Serializable
data class MessageData(
    val response: MessageResponse
)