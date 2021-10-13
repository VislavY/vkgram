package ru.vyapps.vkgram.conversations.repositories

import ru.vyapps.vkgram.vk_api.data.UserData

interface UserRepo {

    suspend fun getUsersById(
        accessToken: String,
        vararg ids: Int
    ): UserData
}