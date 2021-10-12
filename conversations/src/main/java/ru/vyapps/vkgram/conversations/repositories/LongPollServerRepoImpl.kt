package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.LongPollServerResponse
import javax.inject.Inject

class LongPollServerRepoImpl @Inject constructor(
    private val vkService: VkService
) : LongPollServerRepo {

    override suspend fun getLongPollServer(accessToken: String): LongPollServerResponse {
        return vkService.getLongPollServer(accessToken)
    }
}