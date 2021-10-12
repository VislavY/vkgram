package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.message_history.Message
import ru.vyapps.vkgram.message_history.mappers.MessageDataMapper
import ru.vyapps.vkgram.vk_api.VkService
import javax.inject.Inject

class MessageRepoImpl @Inject constructor(
    private val vkService: VkService,
    private val messageDataMapper: MessageDataMapper
) : MessageRepo {

    override suspend fun getMessages(
        conversationId: Long,
        count: Int,
        offset: Int,
        accessToken: String
    ): List<Message> {
        val messageData = vkService.getMessagesByConversationId(
            accessToken,
            conversationId,
            count,
            offset
        )
        return messageDataMapper.map(messageData)
    }
}