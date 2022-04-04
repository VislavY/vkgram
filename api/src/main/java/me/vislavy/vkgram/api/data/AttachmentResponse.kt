package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentResponse(
    val response: AttachmentItems
)

@Serializable
data class AttachmentItems(
    val items: List<DialogAttachment>
)

@Serializable
data class DialogAttachment(
    @SerialName("message_id") val messageId: Int,
    val attachment: Attachment
)

@Serializable
data class Attachment(
    val type: AttachmentType,
    val photo: Photo? = null,
    val video: Video? = null,
    val sticker: Sticker? = null
)
