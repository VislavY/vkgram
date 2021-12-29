package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.AttachmentType

@Serializable
data class Attachment(
    val type: AttachmentType,
    val photo: Photo? = null,
    val video: Video? = null
)
