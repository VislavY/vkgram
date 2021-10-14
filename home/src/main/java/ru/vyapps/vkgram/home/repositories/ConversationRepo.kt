package ru.vyapps.vkgram.home.repositories

import ru.vyapps.vkgram.home.Conversation

interface ConversationRepo {

    suspend fun getConversations(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<Conversation>
}