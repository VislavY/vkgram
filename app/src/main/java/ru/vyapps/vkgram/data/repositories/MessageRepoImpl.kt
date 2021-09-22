package ru.vyapps.vkgram.data.repositories

import retrofit2.await
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.mappers.ConversationDataMapper
import ru.vyapps.vkgram.data.mappers.MessageHistoryDataMapper
import ru.vyapps.vkgram.vk_api.Message
import ru.vyapps.vkgram.vk_api.VkService
import javax.inject.Inject

class MessageRepoImpl @Inject constructor(
    private val vkService: VkService,
    private val conversationDataMapper: ConversationDataMapper,
    private val messageHistoryDataMapper: MessageHistoryDataMapper
) : MessageRepo {

    override suspend fun getConversations(
        count: Int,
        offset: Int,
        token: String
    ): List<Conversation> {
        val conversationData = vkService.getConversations(
            count,
            offset,
            token
        )
        return conversationDataMapper.map(conversationData)
    }

    override suspend fun getMessagesByConversationId(
        conversationId: Long,
        messageCount: Int,
        offset: Int,
        token: String
    ): List<Message> {
        val messageHistoryData = vkService.getMessagesByConversationId(
            conversationId,
            messageCount,
            offset,
            token
        )
        return messageHistoryDataMapper.map(messageHistoryData)
    }

    override suspend fun sendMessage(
        conversationId: Long,
        text: String,
        token: String
    ) {
        vkService.sendMessage(
            conversationId,
            text,
            token
        )
    }
}