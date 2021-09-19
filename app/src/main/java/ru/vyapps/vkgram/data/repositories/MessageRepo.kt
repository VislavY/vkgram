package ru.vyapps.vkgram.data.repositories

import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.remote.Message

interface MessageRepo {

    suspend fun getConversations(
        count: Int,
        offset: Int,
        token: String
    ): List<Conversation>

    suspend fun getMessagesByConversationId(
        conversationId: Long,
        messageCount: Int,
        offset: Int,
        token: String
    ): List<Message>

    suspend fun sendMessage(
        conversationId: Long,
        text: String,
        token: String
    )
}