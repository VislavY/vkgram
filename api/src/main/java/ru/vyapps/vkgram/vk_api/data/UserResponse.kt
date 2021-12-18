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
    @SerialName("photo_400_orig") val photoOrig: String = "",
    @SerialName("bdate") val birthDay: String = "",
    val status: String = "",
    val relation: Int = 0,
    val city: City? = null,
    @SerialName("home_town") val homeTown: String = "",
    @SerialName("personal") val personalInformation: PersonalInformation? = null,
    val relatives: List<Relative> = emptyList(),
    val about: String = "",
    val interests: String = "",
    val music: String = "",
    val films: String = "",
    val books: String = "",
    val games: String = "",
    val quotes: String = "",
    val counters: Counters? = null
)

@Serializable
data class LastSeen(
    val platform: Int,
    @Serializable(DateSerializer::class) val time: Date
)