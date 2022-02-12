package me.vislavy.vkgram.message_history.models

import androidx.compose.ui.text.input.TextFieldValue

sealed class MessageHistoryIntent {
    data class EnterScreen(val conversationId: Int) : MessageHistoryIntent()
    object ReloadScreen : MessageHistoryIntent()
    data class UpdateYourMessageText(val messageText: TextFieldValue) : MessageHistoryIntent()
    object SendMessage : MessageHistoryIntent()
    data class IncreaseMessageList(val currentListSize: Int) : MessageHistoryIntent()
}
