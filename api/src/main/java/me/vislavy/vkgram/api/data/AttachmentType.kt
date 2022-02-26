package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttachmentType {
    @SerialName("photo") Photo,
    @SerialName("video") Video,
    @SerialName("audio") Audio,
    @SerialName("audio_message") AudioMessage,
    @SerialName("call") Call,
    @SerialName("doc") Document,
    @SerialName("link") Link,
    @SerialName("market") Market,
    @SerialName("market_album") MarketAlbum,
    @SerialName("vkpay") VkPay,
    @SerialName("wall") Wall,
    @SerialName("wall_reply") WallReply,
    @SerialName("sticker") Stickers,
    @SerialName("gift") Gift
}