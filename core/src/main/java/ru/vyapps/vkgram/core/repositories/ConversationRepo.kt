package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.core.Conversation
import ru.vyapps.vkgram.vk_api.data.Chat

interface ConversationRepo {

    suspend fun createChat(
        accessToken: String,
        userIds: List<Int>,
        title: String
    ): Int

    suspend fun getConversations(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<Conversation>

    suspend fun getChatById(
        accessToken: String,
        id: Int
    ): Chat
}