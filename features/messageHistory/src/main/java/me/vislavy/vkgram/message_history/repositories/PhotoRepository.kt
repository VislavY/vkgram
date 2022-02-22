package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.api.data.UploadFileResult
import me.vislavy.vkgram.api.data.UploadServer
import okhttp3.MultipartBody

interface PhotoRepository {

    suspend fun getMessagesUploadServer(): UploadServer

    suspend fun uploadFile(
        uploadServerUrl: String,
        file: MultipartBody.Part
    ): UploadFileResult

    suspend fun saveMessagePhoto(
        photo: String,
        server: Int,
        hash: String
    ): Photo
}