package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.User
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : FriendRepository {

    override suspend fun getFriendList(
        count: Int,
        offset: Int
    ) = vkService.getFriendList(
        accessToken = vkAccessToken.accessToken,
        count = count,
        offset = offset
    ).response.friends

    override suspend fun findFriendsByName(name: String, count: Int) = vkService.findFriendsByName(
        accessToken = vkAccessToken.accessToken,
        name = name,
        count = count
    ).response.friends

    override suspend fun acceptFriendById(id: Int) {
        vkService.acceptFriend(vkAccessToken.accessToken, id)
    }

    override suspend fun deleteFriendById(id: Int) {
        vkService.deleteFriend(vkAccessToken.accessToken, id)
    }
}