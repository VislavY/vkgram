package me.vislavy.vkgram.core

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.api.data.ConversationProperties
import me.vislavy.vkgram.api.data.PushSettings
import me.vislavy.vkgram.api.data.SortId

@Serializable
data class ConversationModel(
    val properties: ConversationProperties = ConversationProperties(),
    val title: String = "",
    val photo: String = "",
    val lastMessage: Message? = null,
    val lastMessageLocalId: Int = 0,
    val lastReadMessageLocalId: Int = 0,
    val lastMessageAuthor: String = "",
    val unreadMessageCount: Int = 0,
    val onlineIndicatorEnabled: Boolean = false,
    val memberCount: Int = 0,
    val pushSettings: PushSettings = PushSettings(),
    val sortId: SortId = SortId()
) {
    override fun equals(other: Any?): Boolean {
        if (other is ConversationModel) {
            return (properties.id == other.properties.id)
        }

        return false
    }

    override fun hashCode(): Int {
        return (properties.id * 31)
    }
}