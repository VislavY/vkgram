package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Relative(
    val id: Int,
    val name: String = "",
    val type: RelativeType
)
