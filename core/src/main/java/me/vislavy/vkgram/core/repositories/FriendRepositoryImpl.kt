package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.FriendStatus
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

    override suspend fun findFriends(name: String, count: Int) = vkService.findFriendsByName(
        accessToken = vkAccessToken.accessToken,
        name = name,
        count = count
    ).response.friends

    override suspend fun addFriend(uid: Int) {
        vkService.acceptFriend(vkAccessToken.accessToken, uid)
    }

    override suspend fun deleteFriend(uid: Int) {
        vkService.deleteFriend(vkAccessToken.accessToken, uid)
    }

    override suspend fun getFriendsStatus(uids: List<Int>) =
        vkService.areFriends(vkAccessToken.accessToken, uids).response
}