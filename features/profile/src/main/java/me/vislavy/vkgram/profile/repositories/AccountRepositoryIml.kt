package me.vislavy.vkgram.profile.repositories

import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
import javax.inject.Inject

class AccountRepositoryIml @Inject constructor(
    private val vkService: VkService,
    private val vkAccessToken: VkAccessToken
) : AccountRepository {

    override suspend fun setSilenceMode(dialogId: Int, sound: Byte, time: Int) =
        vkService.setSilenceMode(
            accessToken = vkAccessToken.accessToken,
            dialogId = dialogId,
            sound = sound,
            time = time
        )
}