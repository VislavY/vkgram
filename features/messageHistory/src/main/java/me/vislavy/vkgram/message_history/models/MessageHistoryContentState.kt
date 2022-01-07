package me.vislavy.vkgram.message_history.models

import me.vislavy.vkgram.api.data.Message

sealed class MessageHistoryContentState {
    object Loading : MessageHistoryContentState()
    object Error : MessageHistoryContentState()
    data class Display(val messages: List<Message> = emptyList()) : MessageHistoryContentState()
}