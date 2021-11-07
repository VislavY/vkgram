package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.vk_api.data.Chat

interface ConversationRepository {

    suspend fun createChat(
        accessToken: String,
        userIds: List<Int>,
        title: String
    ): Int

    suspend fun fetchConversationList(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<ConversationModel>

    suspend fun fetchChatById(
        accessToken: String,
        id: Int
    ): Chat
}