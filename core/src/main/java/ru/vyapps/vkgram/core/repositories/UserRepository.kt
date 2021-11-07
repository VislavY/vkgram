package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.data.User

interface UserRepository {

    suspend fun fetchUserListByIds(
        accessToken: String,
        ids: List<Int>
    ): List<User>
}