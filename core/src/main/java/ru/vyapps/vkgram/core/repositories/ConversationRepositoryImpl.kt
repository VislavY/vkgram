package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.core.mappers.ConversationDataMapper
import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.Chat
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