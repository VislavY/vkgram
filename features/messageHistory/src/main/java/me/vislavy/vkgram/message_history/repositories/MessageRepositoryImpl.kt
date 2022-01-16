package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.LastActivity
import me.vislavy.vkgram.api.data.Message
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : MessageRepository {

    override suspend fun getMessageList(
        conversationId: Int,
        count: Int,
        offset: Int
    ): List<Message> {
        return vkService.getMessageListByConversationId(
            accessToken = vkAccessToken.accessToken,
            conversationId = conversationId,
            count = count,
            offset = offset
        ).response.items
    }

    override suspend fun sendMessage(conversationId: Int, text: String) {
        vkService.sendMessage(
            accessToken = vkAccessToken.accessToken,
            conversationId = conversationId,
            text = text
        )
    }

    override suspend fun getLastActivity(userId: Int): LastActivity {
        return vkService.getLastActivity(vkAccessToken.accessToken, userId).response
    }
}