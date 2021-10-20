package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.message_history.Message

interface MessageRepo {

    suspend fun getMessages(
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
}