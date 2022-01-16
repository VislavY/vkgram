package me.vislavy.vkgram.api.data

import kotlinx.serialization.Serializable
import me.vislavy.vkgram.api.serializers.BooleanSerializer
import me.vislavy.vkgram.api.serializers.DateSerializer
import java.util.*

@Serializable
data class LastActivityResponse(
    val response: LastActivity
)

@Serializable
data class LastActivity(
    @Serializable(BooleanSerializer::class) val online: Boolean,
    @Serializable(DateSerializer::class) val time: Date
)
