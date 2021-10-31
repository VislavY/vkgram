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
        accessToken: String,
        conversationId: Int,
        count: Int,
        offset: Int
    ): List<Message> {
        val messageData = vkService.fetchMessageListByConversationId(
            accessToken,
            conversationId,
            count,
            offset
        )
        return messageDataMapper.map(messageData)
    }

    override suspend fun sendMessage(
        accessToken: String,
        conversationId: Int,
        text: String
    ) {
        vkService.sendMessage(
            accessToken = accessToken,
            conversationId = conversationId,
            text = text
        )
    }
}