package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
    @SerialName("product_id") val productId: Int,
    @SerialName("sticker_id") val stickerId: Int,
    val images: List<PhotoSize>,
    @SerialName("images_with_background") val imagesWithBackground: List<PhotoSize>
)
