package me.vislavy.vkgram.home.repositories

import me.vislavy.vkgram.api.data.LongPollServerResponse

interface LongPollServerRepo {

    suspend fun getLongPollServer(accessToken: String): LongPollServerResponse
}