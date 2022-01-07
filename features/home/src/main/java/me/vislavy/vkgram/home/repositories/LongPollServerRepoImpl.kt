package me.vislavy.vkgram.home.repositories

import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.LongPollServerResponse
import javax.inject.Inject

class LongPollServerRepoImpl @Inject constructor(
    private val vkService: VkService
) : LongPollServerRepo {

    override suspend fun getLongPollServer(accessToken: String): LongPollServerResponse {
        return vkService.getLongPollServer(accessToken)
    }
}