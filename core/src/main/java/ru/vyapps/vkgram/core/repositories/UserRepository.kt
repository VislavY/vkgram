package ru.vyapps.vkgram.core.repositories

import ru.vyapps.vkgram.vk_api.data.User

interface UserRepository {

    suspend fun getUserListById(ids: List<Int>): List<User>
}