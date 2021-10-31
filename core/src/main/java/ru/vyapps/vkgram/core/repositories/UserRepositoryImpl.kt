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
        return vkService.fetchUserListByIds(accessToken, ids).response
    }

    override suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User> {
        return vkService.fetchFriendList(
            accessToken = accessToken,
            count = count,
            offset = offset
        ).response.friends
    }

    override suspend fun addFriend(accessToken: String, id: Int) {
        vkService.acceptFriend(accessToken, id)
    }

    override suspend fun deleteFriend(accessToken: String, id: Int) {
        vkService.deleteFriend(accessToken, id)
    }
}