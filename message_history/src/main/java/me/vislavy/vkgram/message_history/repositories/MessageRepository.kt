package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.data.LastActivity
import me.vislavy.vkgram.api.data.Message

interface MessageRepository {

    suspend fun fetchMessageList(
        accessToken: String,
        conversationId: Int,
        count: Int,
        offset: Int
    ): List<Message>

    suspend fun sendMessage(
        accessToken: String,
        conversationId: Int,
        text: String
    )

    suspend fun getLastActivity(
        accessToken: String,
        userId: Int
    ): LastActivity
}