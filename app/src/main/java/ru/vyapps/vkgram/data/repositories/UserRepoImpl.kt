package ru.vyapps.vkgram.data.repositories

import retrofit2.await
import ru.vyapps.vkgram.data.mappers.UserDataMapper
import ru.vyapps.vkgram.data.remote.User
import ru.vyapps.vkgram.data.remote.VkService
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val vkService: VkService,
    private val userDataMapper: UserDataMapper
) : UserRepo {

    override suspend fun getUserById(
        id: Long,
        token: String
    ): User {
        val userData = vkService.getUserById(id, token).await()
        return userDataMapper.map(userData)
    }
}