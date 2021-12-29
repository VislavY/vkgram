package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.data.User

interface FriendRepository {

    suspend fun fetchFriendList(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User>

    suspend fun acceptFriendById(accessToken: String, id: Int)

    suspend fun deleteFriendById(accessToken: String, id: Int)
}