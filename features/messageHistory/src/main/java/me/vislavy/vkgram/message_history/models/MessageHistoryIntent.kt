package me.vislavy.vkgram.message_history.models

import androidx.compose.ui.text.input.TextFieldValue
import java.io.File

sealed class MessageHistoryIntent {
    data class EnterScreen(val conversationId: Int) : MessageHistoryIntent()
    object ReloadScreen : MessageHistoryIntent()
    data class UpdateYourMessageText(val value: TextFieldValue) : MessageHistoryIntent()
    object SendMessage : MessageHistoryIntent()
    data class IncreaseMessageList(val currentListSize: Int) : MessageHistoryIntent()
    data class UpdateSelectedAttachmentList(val value: File) : MessageHistoryIntent()
    object ClearSelectedAttachmentList : MessageHistoryIntent()
    data class UploadAttachment(val value: File) : MessageHistoryIntent()
    data class RemoveAttachment(val value: File) : MessageHistoryIntent()
}
