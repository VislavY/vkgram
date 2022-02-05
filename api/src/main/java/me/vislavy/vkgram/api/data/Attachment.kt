package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    val type: AttachmentType,
    val photo: Photo? = null,
    val video: Video? = null,
    val sticker: Sticker? = null
)
