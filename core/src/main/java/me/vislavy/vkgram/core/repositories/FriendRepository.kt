package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.data.FriendStatus
import me.vislavy.vkgram.api.data.User

interface FriendRepository {

    suspend fun getFriendList(count: Int, offset: Int): List<User>

    suspend fun findFriends(name: String, count: Int): List<User>

    suspend fun addFriend(uid: Int)

    suspend fun deleteFriend(uid: Int)

    suspend fun getFriendsStatus(uids: List<Int>): List<FriendStatus>
}