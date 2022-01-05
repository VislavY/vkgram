package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.api.data.Chat
import me.vislavy.vkgram.api.data.ConversationByIdResponse

interface ConversationRepository {

    suspend fun getChat(
        userIds: List<Int>,
        title: String
    ): Int

    suspend fun getConversationList(
        count: Int,
        offset: Int
    ): List<ConversationModel>

    suspend fun getConversationsByIds(ids: String): List<ConversationModel>

    suspend fun getConversationsByName(
        name: String,
        count: Int
    ): List<ConversationModel>

    suspend fun getChatById(id: Int): Chat
}