package ru.vyapps.vkgram.message_history.repositories

import ru.vyapps.vkgram.vk_api.VkService
import ru.vyapps.vkgram.vk_api.data.Group
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