package me.vislavy.vkgram.message_history.models

sealed class MessageHistoryIntent {
    data class EnterScreen(val conversationId: Int) : MessageHistoryIntent()
    object ReloadScreen : MessageHistoryIntent()
    data class UpdateYourMessageText(val text: String) : MessageHistoryIntent()
    object SendMessage : MessageHistoryIntent()
    data class IncreaseMessageList(val currentListSize: Int) : MessageHistoryIntent()
}
