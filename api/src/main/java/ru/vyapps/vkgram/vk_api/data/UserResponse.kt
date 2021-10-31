package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.vk_api.utils.DateSerializer
import java.util.*

@Serializable
data class UserResponse(
    val response: List<User>
)

@Serializable
data class User(
    val id: Int,
    val domain: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val online: Int = 0,
    @SerialName("last_seen") val lastSeen: LastSeen? = null,
    @SerialName("photo_50") val photo50Url: String,
    @SerialName("photo_100") val photo100Url: String,
    @SerialName("photo_200") val photo200Url: String,
    @SerialName("photo_400_orig") val photo400OrigUrl: String
)

@Serializable
data class LastSeen(
    val platform: Int,
    @Serializable(DateSerializer::class) val time: Date
)