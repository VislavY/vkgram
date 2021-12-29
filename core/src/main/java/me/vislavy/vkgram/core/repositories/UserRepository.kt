package me.vislavy.vkgram.core.repositories

import me.vislavy.vkgram.api.data.User

interface UserRepository {

    suspend fun getUserListById(ids: List<Int>): List<User>
}