package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.UserData
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val vkService: VkService
) : UserRepo {

    override suspend fun getUsersById(
        accessToken: String,
        vararg ids: Int
    ): UserData {
        return vkService.getUserById(accessToken, ids)
    }
}