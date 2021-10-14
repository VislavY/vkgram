package ru.vyapps.vkgram.home.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.FriendData
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

    override suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): FriendData {
        return vkService.getFriends(
            accessToken = accessToken,
            count = count,
            offset = offset
        )
    }

    override suspend fun addFriend(accessToken: String, id: Int) {
        vkService.addFriend(accessToken, id)
    }

    override suspend fun deleteFriend(accessToken: String, id: Int) {
        vkService.deleteFriend(accessToken, id)
    }
}