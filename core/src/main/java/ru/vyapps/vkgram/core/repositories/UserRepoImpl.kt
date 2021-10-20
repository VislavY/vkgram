package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.FriendData
import ru.vyapps.vkgram.vk_api.data.User
import ru.vyapps.vkgram.vk_api.data.UserResponse
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val vkService: VkService
) : UserRepo {

    override suspend fun getUsersById(
        accessToken: String,
        vararg ids: Int
    ): List<User> {
        return vkService.getUserById(accessToken, ids).response
    }

    override suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User> {
        return vkService.getFriends(
            accessToken = accessToken,
            count = count,
            offset = offset
        ).response.friends
    }

    override suspend fun addFriend(accessToken: String, id: Int) {
        vkService.addFriend(accessToken, id)
    }

    override suspend fun deleteFriend(accessToken: String, id: Int) {
        vkService.deleteFriend(accessToken, id)
    }
}