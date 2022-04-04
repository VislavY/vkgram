package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendStatusResponse(
    val response: List<FriendStatus> = emptyList()
)

@Serializable
data class FriendStatus(
    @SerialName("friend_status") val status: Int = 0,
    @SerialName("user_id") val uid: Int = 0
)
