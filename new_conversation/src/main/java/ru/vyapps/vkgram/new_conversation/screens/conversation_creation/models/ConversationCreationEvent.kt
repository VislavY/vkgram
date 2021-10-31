package ru.vyapps.vkgram.new_conversation.screens.conversation_creation.models

import ru.vyapps.vkgram.new_conversation.UserModel
import java.io.File

sealed class ConversationCreationEvent {
    data class EnterScreen(
        val members: List<UserModel>
    ) : ConversationCreationEvent()
    data class RemoveMember(
        val member: UserModel
    ) : ConversationCreationEvent()
    data class ConversationPhotoSelected(
        val photoFile: File
    ) : ConversationCreationEvent()
    object CreateConversation : ConversationCreationEvent()
}