package ru.vyapps.vkgram.conversations

import java.util.*

data class Conversation(
    val id: Long,
    val type: String,
    var avatar: String = "",
    var title: String = "",
    var lastMessage: String = "",
    var lastMessageDate: Date
)