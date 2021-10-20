package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.vk_api.data.Group

interface GroupRepo {

    suspend fun getGroupById(
        accessToken: String,
        id: Int
    ): Group
}