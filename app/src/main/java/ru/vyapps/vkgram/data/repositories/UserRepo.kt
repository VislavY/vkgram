package ru.vyapps.vkgram.data.repositories

import ru.vyapps.vkgram.vk_api.User

interface UserRepo {

    suspend fun getUserById(id: Long, token: String): User
}