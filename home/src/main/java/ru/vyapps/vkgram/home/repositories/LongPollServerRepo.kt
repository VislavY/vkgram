package ru.vyapps.vkgram.home.repositories

import ru.vyapps.vkgram.vk_api.data.LongPollServerResponse

interface LongPollServerRepo {

    suspend fun getLongPollServer(accessToken: String): LongPollServerResponse
}