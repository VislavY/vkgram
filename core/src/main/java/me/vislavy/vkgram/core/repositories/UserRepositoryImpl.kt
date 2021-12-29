package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : UserRepository {

    override suspend fun getUserListById(ids: List<Int>): List<User> {
        return vkService.getUserListByIds(vkAccessToken.accessToken, ids).response
    }
}