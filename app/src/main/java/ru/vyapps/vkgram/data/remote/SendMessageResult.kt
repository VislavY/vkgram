package ru.vyapps.vkgram.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SentMessageResult(
    @SerialName("peer_id") val conversationId: Long,
    @SerialName("message_id") val messageId: Long,
    @SerialName("conversation_message_id") val conversationMessageId: Long,
    val error: Int
)
