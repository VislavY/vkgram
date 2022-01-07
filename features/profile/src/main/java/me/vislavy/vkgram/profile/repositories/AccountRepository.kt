package me.vislavy.vkgram.profile.repositories

import me.vislavy.vkgram.api.data.ProfileInfo

interface AccountRepository {

    suspend fun getProfileInfo(): ProfileInfo
}