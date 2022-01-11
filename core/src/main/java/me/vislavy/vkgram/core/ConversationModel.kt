package me.vislavy.vkgram.core

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.api.data.conversation.ConversationProperties

@Serializable
data class ConversationModel(
    val properties: ConversationProperties = ConversationProperties(),
    val title: String = "",
    val photo: String = "",
    val lastMessage: Message? = null,
    val lastMessageLocalId: Int = 0,
    val lastReadMessageLocalId: Int = 0,
    val unreadMessageCount: Int = 0,
    val onlineIndicatorEnabled: Boolean = false,
    val memberCount: Int = 0
)