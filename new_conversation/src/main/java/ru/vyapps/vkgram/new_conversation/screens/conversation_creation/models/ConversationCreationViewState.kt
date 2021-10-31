package ru.vyapps.vkgram.new_conversation.screens.conversation_creation.models

import ru.vyapps.vkgram.new_conversation.UserModel
import java.io.File

sealed class ConversationCreationViewState {
    object Loading : ConversationCreationViewState()
    object Error : ConversationCreationViewState()
    data class Display(
        val conversationPhoto: File?,
        val items: List<UserModel>,
        var conversationName: String = ""
    ) : ConversationCreationViewState()
}
