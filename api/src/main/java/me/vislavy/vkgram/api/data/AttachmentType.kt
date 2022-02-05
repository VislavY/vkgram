package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttachmentType {
    @SerialName("photo") PHOTO,
    @SerialName("video") VIDEO,
    @SerialName("audio") AUDIO,
    @SerialName("audio_message") AUDIO_MESSAGE,
    @SerialName("call") CALL,
    @SerialName("doc") DOCUMENT,
    @SerialName("link") LINK,
    @SerialName("market") MARKET,
    @SerialName("market_album") MARKET_ALBUM,
    @SerialName("vkpay") VKPAY,
    @SerialName("wall") WALL,
    @SerialName("wall_reply") WALL_REPLY,
    @SerialName("sticker") STICKER,
    @SerialName("gift") GIFT
}