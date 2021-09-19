package ru.vyapps.vkgram.data

import java.util.Date

data class Conversation(
    val id: Long,
    val type: String,
    var avatar: String = "",
    var title: String = "",
    var lastMessage: String = "",
    var lastMessageDate: Date
)