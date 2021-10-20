package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendResponse(
    val response: FriendData
)

@Serializable
data class FriendData(
    @SerialName("items") val friends: List<User>
)