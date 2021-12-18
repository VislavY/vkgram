package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RelativeType {
    @SerialName("child") Child,
    @SerialName("sibling") Sibling,
    @SerialName("parent") Parent,
    @SerialName("grandparent") Grandparent,
    @SerialName("grandchild") Grandchild
}