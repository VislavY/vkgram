package ru.vyapps.vkgram.profile.repositories

import ru.vyapps.vkgram.vk_api.VkAccessToken
import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.ProfileInfo
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : AccountRepository {

    override suspend fun getProfileInfo(): ProfileInfo {
        return vkService.getProfileInfo(vkAccessToken.accessToken).response
    }
}