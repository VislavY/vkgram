package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.data.User

interface UserRepository {

    suspend fun fetchUserListByIds(
        accessToken: String,
        ids: List<Int>
    ): List<User>

    suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User>

    suspend fun addFriend(accessToken: String, id: Int)

    suspend fun deleteFriend(accessToken: String, id: Int)
}