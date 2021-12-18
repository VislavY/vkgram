package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Counters(
    val friends: Int = 0,
    @SerialName("mutual_friends") val commonFriends: Int = 0,
    @SerialName("followers") val subscribes: Int = 0,
)
