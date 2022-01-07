package me.vislavy.vkgram.profile.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.ProfileInfo
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : AccountRepository {

    override suspend fun getProfileInfo(): ProfileInfo {
        return vkService.getProfileInfo(vkAccessToken.accessToken).response
    }
}