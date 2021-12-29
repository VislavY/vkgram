package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupResponse(
    val response: List<Group>
)

@Serializable
data class Group(
    val id: Int,
    val name: String,
    @SerialName("screen_name") val screenName: String,
    @SerialName("is_closed") val isClosed: Int,
    @SerialName("photo_200") val photo: String
)
