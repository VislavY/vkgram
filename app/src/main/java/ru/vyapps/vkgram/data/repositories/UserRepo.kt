package ru.vyapps.vkgram.data.repositories

import ru.vyapps.vkgram.data.remote.User

interface UserRepo {

    suspend fun getUserById(id: Long, token: String): User
}