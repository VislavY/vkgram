package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.data.Friend
import ru.vyapps.vkgram.vk_api.data.FriendData
import ru.vyapps.vkgram.vk_api.data.UserData

interface UserRepo {

    suspend fun getUsersById(
        accessToken: String,
        vararg ids: Int
    ): UserData

    suspend fun getFriends(
        accessToken: String,
        count: Int,
        offset: Int
    ): FriendData

    suspend fun addFriend(accessToken: String, id: Int)

    suspend fun deleteFriend(accessToken: String, id: Int)
}