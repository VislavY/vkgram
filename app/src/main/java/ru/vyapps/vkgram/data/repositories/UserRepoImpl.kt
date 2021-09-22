package ru.vyapps.vkgram.data.repositories

import ru.vyapps.vkgram.data.mappers.UserDataMapper
import ru.vyapps.vkgram.vk_api.User
import ru.vyapps.vkgram.vk_api.VkService
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val vkService: VkService,
    private val userDataMapper: UserDataMapper
) : UserRepo {

    override suspend fun getUserById(
        id: Long,
        token: String
    ): User {
        val userData = vkService.getUserById(id, token)
        return userDataMapper.map(userData)
    }
}