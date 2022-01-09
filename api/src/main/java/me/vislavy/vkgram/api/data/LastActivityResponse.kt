package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.serializers.DateSerializer
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
