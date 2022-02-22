package me.vislavy.vkgram.message_history.models

import androidx.compose.ui.text.input.TextFieldValue
import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.core.ConversationModel
import java.io.File

sealed class MessageHistoryViewState {
    object Loading : MessageHistoryViewState()
    object Error : MessageHistoryViewState()
    data class Display(
        val conversation: ConversationModel? = null,
        val topBarSubtitle: String = "",
        val messages: List<Message> = emptyList(),
        val messageText: TextFieldValue = TextFieldValue(),
        val selectedAttachments: List<File> = emptyList(),
        val attachments: List<String> = emptyList()
    ) : MessageHistoryViewState()
}