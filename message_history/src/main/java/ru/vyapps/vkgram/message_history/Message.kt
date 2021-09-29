package ru.vyapps.vkgram.message_history

import java.util.*

data class Message(
    val id: Int,
    val userId: Int,
    val conversationId: Int,
    val text: String,
    val date: Date,
    val out: Int
)
