package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.LastActivity
import ru.vyapps.vkgram.vk_api.data.Message
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val vkService: VkService
) : MessageRepository {

    override suspend fun fetchMessageList(
        accessToken: String,
        conversationId: Int,
        count: Int,
        offset: Int
    ): List<Message> {
        return vkService.fetchMessageListByConversationId(
            accessToken = accessToken,
            conversationId = conversationId,
            count = count,
            offset = offset
        ).response.items
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

    override suspend fun getLastActivity(
        accessToken: String,
        userId: Int
    ): LastActivity {
        return vkService.getLastActivity(accessToken, userId).response
    }
}