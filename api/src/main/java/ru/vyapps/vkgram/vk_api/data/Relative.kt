package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable

@Serializable
data class Relative(
    val id: Int,
    val name: String = "",
    val type: RelativeType
)
