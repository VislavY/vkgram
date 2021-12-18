package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Career(
    @SerialName("group_id") val companyGroupId: Int = 0,
    val company: String = "",
    val position: String = "",
    val from: Int = 0,
    val until: Int = 0
)
