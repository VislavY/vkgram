package ru.vyapps.vkgram.conversations

import ru.vyapps.vkgram.vk_api.data.ChatSettings
import ru.vyapps.vkgram.vk_api.data.Message

data class Conversation(
    val id: Int,
    val type: String,
    var properties: ChatSettings,
    var lastMessage: Message,
    var lastMessageAuthor: String = ""
)