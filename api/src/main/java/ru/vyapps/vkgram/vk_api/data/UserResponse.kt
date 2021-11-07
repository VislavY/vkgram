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
    var online: Int = 0,
    @SerialName("last_seen") val lastSeen: LastSeen? = null,
    @SerialName("photo_200") val photo: String = "",
    @SerialName("photo_400_orig") val photoOrig: String = ""
)

@Serializable
data class LastSeen(
    val platform: Int,
    @Serializable(DateSerializer::class) val time: Date
)