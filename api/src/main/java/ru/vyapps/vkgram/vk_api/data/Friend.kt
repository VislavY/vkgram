package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendData(
    val response: FriendResponse
)

@Serializable
data class FriendResponse(
    @SerialName("items") val friends: List<Friend>
)

@Serializable
data class Friend(
    val id: Int,
    val domain: String,
    @SerialName("photo_200") val photoUrl: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
)
