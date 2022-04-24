package me.vislavy.vkgram.core.repositories

import com.vk.api.sdk.VK.executeSync
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersFields
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.vislavy.vkgram.api.local.database.StoredUserDao
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.api.local.entities.convertToStoredUser
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val storedUserDao: StoredUserDao
) {

    suspend fun fetchUser(id: Long): StoredUser =
        withContext(Dispatchers.IO) {
            val request = UsersService().usersGet(
                userIds = listOf(UserId(id)),
                fields = listOf(
                    UsersFields.STATUS, UsersFields.LAST_SEEN, UsersFields.COUNTERS,
                    UsersFields.BDATE, UsersFields.COUNTRY, UsersFields.CITY,
                    UsersFields.PHOTO_MAX, UsersFields.DOMAIN, UsersFields.VERIFIED,
                    UsersFields.FRIEND_STATUS, UsersFields.CAN_WRITE_PRIVATE_MESSAGE,
                    UsersFields.FIRST_NAME_GEN, UsersFields.LAST_NAME_GEN, UsersFields.SEX
                ),
            )
            val response = executeSync(request).first()
            val storedUser = response.convertToStoredUser()
            storedUserDao.insert(storedUser)
            storedUser
        }

    suspend fun fetchLocalUser(id: Long): StoredUser? = storedUserDao.fetch(id)
}