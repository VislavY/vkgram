package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val response: Chat
)

@Serializable
data class Chat(
    val id: Int,
    val title: String,
    @SerialName("members_count") val membersCount: Int,
    @SerialName("admin_id") val adminId: Int,
    val users: List<Int>,
    @SerialName("is_default_photo") val isDefaultPhoto: Boolean,
    @SerialName("photo_50") val photo50Url: String,
    @SerialName("photo_100") val photo100Url: String,
    @SerialName("photo_200") val photo200Url: String
)
