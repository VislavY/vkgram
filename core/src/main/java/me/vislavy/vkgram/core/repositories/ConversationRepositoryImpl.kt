package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.mappers.ConversationDataMapper
import me.vislavy.vkgram.api.VkService
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken,
    private val conversationDataMapper: ConversationDataMapper
) : ConversationRepository {

    override suspend fun getChat(
        userIds: List<Int>,
        title: String
    ) = vkService.createChat(
        accessToken = vkAccessToken.accessToken,
        userIds = userIds,
        title = title
    ).response

    override suspend fun getConversationList(
        count: Int,
        offset: Int
    ): List<ConversationModel> {
        val conversationData = vkService.getConversationList(
            accessToken = vkAccessToken.accessToken,
            count = count,
            offset = offset
        ).response
        return conversationDataMapper.map(conversationData)
    }

    override suspend fun getConversationsByIds(ids: String): List<ConversationModel> {
        val conversationByIdResponse = vkService.getConversationListById(
            accessToken = vkAccessToken.accessToken,
            ids = ids
        ).response ?: return emptyList()
        return conversationDataMapper.map(conversationByIdResponse)
    }

    override suspend fun getConversationsByName(
        name: String,
        count: Int
    ): List<ConversationModel> {
        val conversationData = vkService.findConversation(
            accessToken = vkAccessToken.accessToken,
            name = name,
            count = count
        ).response ?: return emptyList()
        return conversationDataMapper.map(conversationData)
    }

    override suspend fun getChatById(id: Int) =
        vkService.getChatById(vkAccessToken.accessToken, id).response
}