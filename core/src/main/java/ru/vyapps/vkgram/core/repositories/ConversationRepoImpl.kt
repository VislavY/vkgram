package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.core.Conversation
import ru.vyapps.vkgram.core.mappers.ConversationDataMapper
import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.Chat
import javax.inject.Inject

 class ConversationRepoImpl @Inject constructor(
    private val vkService: VkService,
    private val conversationDataMapper: ConversationDataMapper
) : ConversationRepo {

    override suspend fun getConversations(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<Conversation> {
        val conversationData = vkService.getConversations(
            accessToken = accessToken,
            count = count,
            offset = offset
        )
        return conversationDataMapper.map(conversationData)
    }

     override suspend fun getChatById(
         accessToken: String,
         id: Int
     ): Chat {
         return vkService.getChatById(accessToken, id).response
     }
 }