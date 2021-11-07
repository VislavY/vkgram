package ru.vyapps.vkgram.vk_api.data

import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.vk_api.utils.DateSerializer
import java.util.*

@Serializable
data class LastActivityResponse(
    val response: LastActivity
)

@Serializable
data class LastActivity(
    val online: Int,
    @Serializable(DateSerializer::class) val time: Date
)
