package me.vislavy.vkgram.core.repositories

import com.vk.api.sdk.VK.executeSync
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.friends.FriendsService
import com.vk.sdk.api.friends.dto.FriendsAddResponse
import com.vk.sdk.api.friends.dto.FriendsDeleteResponse
import com.vk.sdk.api.friends.dto.FriendsFriendStatusStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FriendRepository @Inject constructor() {

    suspend fun fetchFriendStatus(userId: Long): FriendsFriendStatusStatus =
        withContext(Dispatchers.IO) {
            val request = FriendsService().friendsAreFriends(listOf(UserId(userId)))
            executeSync(request).last().friendStatus
        }

    suspend fun followOrAddFriend(userId: Long): FriendsAddResponse =
        withContext(Dispatchers.IO) {
            val request = FriendsService().friendsAdd(UserId(userId))
            executeSync(request)
        }

    suspend fun unfollowOrUnfriend(userId: Long): FriendsDeleteResponse =
        withContext(Dispatchers.IO) {
            val request = FriendsService().friendsDelete(UserId(userId))
            executeSync(request)
        }
}