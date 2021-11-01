package ru.vyapps.vkgram.core

import ru.vyapps.vkgram.vk_api.data.ChatSettings
import ru.vyapps.vkgram.vk_api.data.Message
import ru.vyapps.vkgram.vk_api.data.User

data class Conversation(
    val id: Int,
    val type: String,
    var properties: ChatSettings,
    val unreadMessageCount: Int,
    val lastReadMessageId: Int,
    var lastMessage: Message,
    var lastMessageAuthor: String = "",
    var user: User? = null
)