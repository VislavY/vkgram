package me.vislavy.vkgram.message_history.models

import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.core.ConversationModel

sealed class MessageHistoryViewState {
    object Loading : MessageHistoryViewState()
    object Error : MessageHistoryViewState()
    data class Display(
        val conversation: ConversationModel? = null,
        val topBarSubtitle: String = "",
        val messages: List<Message> = emptyList(),
        val yourMessageText: String = ""
    ) : MessageHistoryViewState()
}