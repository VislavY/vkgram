package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.vk_api.AttachmentType

@Serializable
data class Attachment(
    val type: AttachmentType,
    val photo: Photo? = null,
    val video: Video? = null
)
