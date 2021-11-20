package ru.vyapps.vkgram.profile.repositories

import ru.vyapps.vkgram.vk_api.data.ProfileInfo

interface AccountRepository {

    suspend fun getProfileInfo(): ProfileInfo
}