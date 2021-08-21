package ru.vyapps.vkgram.data

import kotlinx.serialization.Serializable
import ru.vyapps.vkgram.utils.VKDate

@Serializable
data class Conversation(
    val id: Long,
    val type: String,
    var avatar: String = "",
    var title: String = "",
    val last_message: String = "",
    val last_message_date: VKDate
)