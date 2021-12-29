package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoResponse(
    val response: ProfileInfo
)

@Serializable
data class ProfileInfo(
    @SerialName("first_name") val firstName: String = "",
    @SerialName("last_name") val lastName: String = "",
    @SerialName("maiden_name") val maidenName: String = "",
    @SerialName("screen_name") val username: String = "",
    val sex: Int = 0,
    val relation: Int = 0,
    @SerialName("relation_pending") val relationPending: User? = null,
    @SerialName("relation_requests") val relationRequests: List<User> = emptyList(),
    @SerialName("bdate") val brightDate: String = "",
    @SerialName("bdate_visibility") val brightDateVisibility: Int = 0,
    @SerialName("home_town") val homeTown: String = "",
    val county: County? = null,
    val city: City? = null,
    val status: String = "",
    val phone: String = ""
)