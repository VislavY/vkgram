package ru.vyapps.vkgram.new_conversation.screens.conversation_creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.EventHandler
import ru.vyapps.vkgram.vk_api.VkAccessToken
import ru.vyapps.vkgram.core.repositories.ConversationRepository
import ru.vyapps.vkgram.new_conversation.UserModel
import ru.vyapps.vkgram.new_conversation.screens.conversation_creation.models.ConversationCreationEvent
import ru.vyapps.vkgram.new_conversation.screens.conversation_creation.models.ConversationCreationViewState
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConversationCreationViewModel @Inject constructor(
    private val vkAccessToken: VkAccessToken,
    private val conversationRepository: ConversationRepository
) : ViewModel(), EventHandler<ConversationCreationEvent> {

    private val _viewState =
        MutableStateFlow<ConversationCreationViewState>(ConversationCreationViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: ConversationCreationEvent) {
        when (val currentState = _viewState.value) {
            is ConversationCreationViewState.Loading -> reduce(event, currentState)
            is ConversationCreationViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(
        event: ConversationCreationEvent,
        currentViewState: ConversationCreationViewState.Loading
    ) {
        when (event) {
            is ConversationCreationEvent.EnterScreen -> performLoadData(event.members)
            else -> throw NotImplementedError("Invalid $event for state $currentViewState")
        }
    }

    private fun reduce(
        event: ConversationCreationEvent,
        currentViewState: ConversationCreationViewState.Display
    ) {
        when (event) {
            is ConversationCreationEvent.EnterScreen -> performLoadData(event.members)
            is ConversationCreationEvent.ConversationPhotoSelected -> performInstallConversationPhoto(
                photoFile = event.photoFile,
                currentViewState = currentViewState
            )
            is ConversationCreationEvent.RemoveMember -> performRemoveMember(
                member = event.member,
                currentViewState = currentViewState
            )
            is ConversationCreationEvent.CreateConversation -> performConversationCreation(currentViewState)
            else -> throw NotImplementedError("Invalid $event for state $currentViewState")
        }
    }

    private fun performLoadData(members: List<UserModel>) {
        _viewState.value = ConversationCreationViewState.Display(null, members)
    }

    private fun performInstallConversationPhoto(
        photoFile: File,
        currentViewState: ConversationCreationViewState.Display
    ) {
        _viewState.value = currentViewState.copy(conversationPhoto = photoFile)
    }

    private fun performRemoveMember(
        member: UserModel,
        currentViewState: ConversationCreationViewState.Display
    ) {
        val newMembers = currentViewState.items.toMutableList()
        newMembers.remove(member)
        _viewState.value = currentViewState.copy(items = newMembers)
    }

    private fun performConversationCreation(currentState: ConversationCreationViewState.Display) {
        viewModelScope.launch {
            try {
                conversationRepository.createChat(
                    accessToken = vkAccessToken.accessToken,
                    userIds = currentState.items.map { user ->
                        user.id
                    },
                    title = currentState.conversationName
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = ConversationCreationViewState.Error
            }
        }
    }

    companion object {
        private const val Tag = "conversation_creation"
    }
}