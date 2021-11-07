package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.vk_api.data.LastActivity
import ru.vyapps.vkgram.vk_api.data.Message

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