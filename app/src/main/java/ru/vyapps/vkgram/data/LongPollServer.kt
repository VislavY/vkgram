package ru.vyapps.vkgram.data

import kotlinx.serialization.Serializable

@Serializable
data class LongPollServer(
    val server: String,
    val key: String,
    var ts: Long
)
