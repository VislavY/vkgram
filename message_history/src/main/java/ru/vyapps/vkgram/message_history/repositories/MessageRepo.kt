package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.message_history.Message

interface MessageRepo {

    suspend fun getMessages(
        conversationId: Long,
        count: Int,
        offset: Int,
        accessToken: String
    ): List<Message>
}