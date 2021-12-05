package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.VkAccessToken
import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : UserRepository {

    override suspend fun getUserListById(ids: List<Int>): List<User> {
        return vkService.getUserListByIds(vkAccessToken.accessToken, ids).response
    }
}