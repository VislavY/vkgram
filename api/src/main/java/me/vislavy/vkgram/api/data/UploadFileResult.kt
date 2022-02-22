package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable

@Serializable
data class UploadFileResult(
    val server: Int,
    val photo: String,
    val hash: String
)
