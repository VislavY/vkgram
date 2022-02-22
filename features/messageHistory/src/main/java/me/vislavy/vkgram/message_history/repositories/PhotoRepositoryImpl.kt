package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import okhttp3.MultipartBody
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val vkService: VkService,
    private val accessToken: VkAccessToken
) : PhotoRepository {

    override suspend fun getMessagesUploadServer() =
        vkService.getMessagesUploadServer(accessToken.accessToken).response

    override suspend fun uploadFile(uploadServerUrl: String, file: MultipartBody.Part) =
        vkService.uploadFile(uploadServerUrl, file)

    override suspend fun saveMessagePhoto(photo: String, server: Int, hash: String) =
        vkService.saveMessagesPhoto(
            accessToken = accessToken.accessToken,
            photo = photo,
            server = server,
            hash = hash
        ).response
}