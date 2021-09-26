package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.conversations.Conversation

interface ConversationRepo {

    suspend fun getConversations(
        count: Int,
        offset: Int,
        accessToken: String
    ): List<Conversation>
}