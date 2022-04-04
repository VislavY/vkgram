package me.vislavy.vkgram.profile.repositories

interface AccountRepository {
    suspend fun setSilenceMode(dialogId: Int, sound: Byte, time: Int = -1): Byte
}