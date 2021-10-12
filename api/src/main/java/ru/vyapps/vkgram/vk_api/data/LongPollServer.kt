package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable

@Serializable
data class LongPollServerResponse(
    val response: LongPollServer
)

@Serializable
data class LongPollServer(
    val server: String,
    val key: String,
    val ts: Int
)
