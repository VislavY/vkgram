package me.vislavy.vkgram.new_conversation

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val domain: String,
    val firstName: String,
    val lastName: String,
    var isSelected: Boolean = false,
    val photo: String = ""
)