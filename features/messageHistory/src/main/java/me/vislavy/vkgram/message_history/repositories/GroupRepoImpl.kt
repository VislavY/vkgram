package me.vislavy.vkgram.message_history.repositories

import me.vislavy.vkgram.api.VkService
import me.vislavy.vkgram.api.data.Group
import javax.inject.Inject

class GroupRepoImpl @Inject constructor(
    private val vkService: VkService
) : GroupRepo {

    override suspend fun getGroupById(
        accessToken: String,
        id: Int
    ): Group {
        return vkService
            .getGroupById(accessToken, id)
            .response
            .first()
    }


}