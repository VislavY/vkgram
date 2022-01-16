package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.data.LastActivity
import me.vislavy.vkgram.api.data.Message

interface MessageRepository {

    suspend fun getMessageList(
        conversationId: Int,
        count: Int,
        offset: Int = 0
    ): List<Message>

    suspend fun sendMessage(conversationId: Int, text: String)

    suspend fun getLastActivity(userId: Int): LastActivity
}