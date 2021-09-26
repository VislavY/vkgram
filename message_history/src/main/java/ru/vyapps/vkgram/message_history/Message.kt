package ru.vyapps.vkgram.message_history

import java.util.*

data class Message(
    val id: Long,
    val userId: Long,
    val conversationId: Long,
    val text: String,
    val date: Date,
    val out: Int
)
