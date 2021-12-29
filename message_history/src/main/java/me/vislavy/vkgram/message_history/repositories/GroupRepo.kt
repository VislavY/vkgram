package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.data.Group

interface GroupRepo {

    suspend fun getGroupById(
        accessToken: String,
        id: Int
    ): Group
}