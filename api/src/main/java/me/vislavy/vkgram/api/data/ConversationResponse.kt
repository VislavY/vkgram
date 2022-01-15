package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationResponse(
    val response: ConversationData
)

@Serializable
data class ConversationData(
    @SerialName("items") val conversations: List<ConversationItem> = emptyList(),
    @SerialName("profiles") val users: List<User> = emptyList(),
    val groups: List<Group> = emptyList()
)

@Serializable
data class ConversationItem(
    val conversation: Conversation,
    @SerialName("last_message") val lastMessage: Message? = null,
)

@Serializable
data class Conversation(
    @SerialName("peer") val properties: ConversationProperties = ConversationProperties(),
    @SerialName("in_read_cmid") val lastMessageLocalId: Int = 0,
    @SerialName("out_read_cmid") val lastReadMessageLocalId: Int = 0,
    @SerialName("unread_count") val unreadMessageCount: Int = 0,
    @SerialName("chat_settings") val chatProperties: ChatProperties? = null,
    @SerialName("push_settings") val pushSettings: PushSettings = PushSettings(),
    @SerialName("sort_id") val sortId: SortId = SortId()
)

@Serializable
data class ConversationProperties(
    val id: Int = 0,
    @SerialName("local_id") val localId: Int = 0,
    val type: ConversationType = ConversationType.Chat
)

@Serializable
enum class ConversationType {
    @SerialName("user") User,
    @SerialName("chat") Chat,
    @SerialName("group") Group
}

@Serializable
data class ChatProperties(
    @SerialName("owner_id") val ownerId: Int = 0,
    val title: String = "",
    val photo: ChatPhoto = ChatPhoto(),
    @SerialName("member_count") val memberCount: Int = 0,
    @SerialName("active_ids") val memberIds: List<Int> = emptyList()
)

@Serializable
data class ChatPhoto(
    @SerialName("photo_50") val photo50: String = "",
    @SerialName("photo_100") val photo100: String = "",
    @SerialName("photo_200") val photo200: String = "",
    @SerialName("is_default_photo") val isDefaultPhoto: Boolean = false
)

@Serializable
data class PushSettings(
    @SerialName("no_sound") val soundDisabled: Boolean = false
)

@Serializable
data class SortId(
    @SerialName("major_id") val majorId: Int = 0,
    @SerialName("minor_id") val minorId: Int = 0
)