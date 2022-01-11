package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.data.conversation.Conversation

@Serializable
data class ConversationByIdData(
    val response: ConversationByIdResponse? = null
)

@Serializable
data class ConversationByIdResponse(
    val items: List<Conversation> = emptyList(),
    val profiles: List<User> = emptyList(),
    val groups: List<Group> = emptyList()
)
