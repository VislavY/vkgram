package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.conversations.Conversation
import ru.vyapps.vkgram.conversations.mappers.ConversationDataMapper
import ru.vyapps.vkgram.vk_api.VkService
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
        val conversationData = vkService.getConversations(accessToken, count, offset)
        return conversationDataMapper.map(conversationData)
    }
 }