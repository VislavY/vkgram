package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.conversations.Conversation

interface ConversationRepo {

    suspend fun getConversations(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<Conversation>
}