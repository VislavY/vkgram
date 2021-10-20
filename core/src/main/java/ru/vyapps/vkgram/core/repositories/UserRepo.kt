package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.data.FriendData
import ru.vyapps.vkgram.vk_api.data.User

interface UserRepo {

    suspend fun getUsersById(
        accessToken: String,
        vararg ids: Int
    ): List<User>

    suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): List<User>

    suspend fun addFriend(accessToken: String, id: Int)

    suspend fun deleteFriend(accessToken: String, id: Int)
}