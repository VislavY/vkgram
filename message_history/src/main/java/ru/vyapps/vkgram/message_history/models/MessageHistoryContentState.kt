package ru.vyapps.vkgram.message_history.models

import ru.vyapps.vkgram.vk_api.data.Message

sealed class MessageHistoryContentState {
    object Loading : MessageHistoryContentState()
    object Error : MessageHistoryContentState()
    data class Display(val messages: List<Message> = emptyList()) : MessageHistoryContentState()
}