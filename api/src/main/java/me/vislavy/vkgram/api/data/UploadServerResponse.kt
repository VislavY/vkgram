package me.vislavy.vkgram.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadServerResponse(
    val response: UploadServer
)

@Serializable
data class UploadServer(
    @SerialName("upload_url") val uploadUrl: String,
    @SerialName("album_id") val AlbumId: Int,
    @SerialName("user_id") val userId: Int
)
