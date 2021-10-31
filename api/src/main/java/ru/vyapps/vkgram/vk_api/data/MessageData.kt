package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.vk_api.utils.DateSerializer
import java.util.*

@Serializable
data class Message(
    val id: Int,
    @SerialName("from_id") val userId: Int,
    @SerialName("peer_id") val ConversationId: Int,
    val text: String,
    val attachments: List<Attachment>,
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