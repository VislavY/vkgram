package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val vkService: VkService
) : UserRepository {

    override suspend fun fetchUserListByIds(
        accessToken: String,
        ids: List<Int>
    ): List<User> {
        return vkService.getUserListByIds(accessToken, ids).response
    }
}