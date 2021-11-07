package ru.vyapps.vkgram.message_history.models

import ru.vyapps.vkgram.core.ConversationModel

sealed class MessageHistoryEvent {
    data class EnterScreen(val conversation: ConversationModel) : MessageHistoryEvent()
    object ReloadScreen : MessageHistoryEvent()
    data class MessageListEnd(val size: Int) : MessageHistoryEvent()
    data class SendMessage(val text: String) : MessageHistoryEvent()
}
