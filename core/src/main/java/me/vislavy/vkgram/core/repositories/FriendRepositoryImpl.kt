package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.User
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val vkService: VkService
) : FriendRepository {

    override suspend fun fetchFriendList(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User> {
        return vkService.getFriendList(
            accessToken = accessToken,
            count = count,
            offset = offset
        ).response.friends
    }

    override suspend fun acceptFriendById(accessToken: String, id: Int) {
        vkService.acceptFriend(accessToken, id)
    }

    override suspend fun deleteFriendById(accessToken: String, id: Int) {
        vkService.deleteFriend(accessToken, id)
    }
}