package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class ChatPhoto(
    @SerialName("photo_200") val photo: String = ""
)

@Serializable
data class ChatSettings(
    var title: String = "",
    var photo: ChatPhoto? = null,
    @SerialName("active_ids") val activeIds: List<Int> = emptyList()
)

@Serializable
data class ConversationPeer(
    val id: Int,
    @SerialName("local_id") val localId: Int,
    val type: String
)

@Serializable
data class Conversation(
    val peer: ConversationPeer,
    @SerialName("out_read") val lastReadMessageId: Int,
    @SerialName("unread_count") val unreadMessageCount: Int = 0,
    @SerialName("chat_settings") val chatSettings: ChatSettings? = null,
)

@Serializable
data class ConversationItem(
    val conversation: Conversation,
    @SerialName("last_message") val lastMessage: Message
)

@Serializable
data class ConversationData(
    val items: List<ConversationItem>,
    val profiles: List<User> = emptyList(),
    val groups: List<Group> = emptyList()
)


@Serializable
data class ConversationResponse(
    val response: ConversationData
)
