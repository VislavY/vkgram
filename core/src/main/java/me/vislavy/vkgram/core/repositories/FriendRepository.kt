package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.data.User

interface FriendRepository {

    suspend fun getFriendList(
        count: Int,
        offset: Int
    ): List<User>

    suspend fun findFriendsByName(
        name: String,
        count: Int
    ): List<User>

    suspend fun acceptFriendById(id: Int)

    suspend fun deleteFriendById(id: Int)
}