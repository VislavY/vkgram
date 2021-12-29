package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.mappers.ConversationDataMapper
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.Chat
import javax.inject.Inject

 class ConversationRepositoryImpl @Inject constructor(
     private val vkService: VkService,
     private val conversationDataMapper: ConversationDataMapper
) : ConversationRepository {

     override suspend fun createChat(
         accessToken: String,
         userIds: List<Int>,
         title: String
     ): Int {
         return vkService.createChat(
             accessToken = accessToken,
             userIds = userIds,
             title = title
         ).response
     }

     override suspend fun fetchConversationList(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<ConversationModel> {
        val conversationData = vkService.getConversationList(
            accessToken = accessToken,
            count = count,
            offset = offset
        ).response
        return conversationDataMapper.map(conversationData)
    }

     override suspend fun fetchChatById(
         accessToken: String,
         id: Int
     ): Chat {
         return vkService.getChatById(accessToken, id).response
     }
 }