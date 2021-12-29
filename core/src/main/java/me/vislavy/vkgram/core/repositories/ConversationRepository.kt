package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.api.data.Chat

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