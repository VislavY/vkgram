package ru.vyapps.vkgram.core

import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.vk_api.data.Message

@Serializable
data class ConversationModel(
    val id: Int,
    val type: String,
    val title: String = "",
    val photo: String = "",
    val userCount: Int = 0,
    val unreadMessageCount: Int = 0,
    val lastReadMessageId: Int = 0,
    val lastMessage: Message? = null,
    val lastMessageAuthor: String = "",
    val indicatorEnabled: Boolean = false,
)