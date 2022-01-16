package me.vislavy.vkgram.message_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.IntentHandler
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.message_history.models.MessageHistoryIntent
import me.vislavy.vkgram.message_history.repositories.MessageRepository
import me.vislavy.vkgram.api.data.ConversationType
import me.vislavy.vkgram.core.repositories.ConversationRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageHistoryViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val messageRepository: MessageRepository
) : ViewModel(), IntentHandler<MessageHistoryIntent> {

    private val _viewState =
        MutableStateFlow<MessageHistoryViewState>(MessageHistoryViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onIntent(intent: MessageHistoryIntent) {
        when (val currentContentState = _viewState.value) {
            is MessageHistoryViewState.Loading -> reduce(intent, currentContentState)
            is MessageHistoryViewState.Error -> reduce(intent, currentContentState)
            is MessageHistoryViewState.Display -> reduce(intent, currentContentState)
        }
    }

    private fun reduce(
        intent: MessageHistoryIntent,
        currentState: MessageHistoryViewState.Loading
    ) {
        when (intent) {
            is MessageHistoryIntent.EnterScreen -> enterScreen(intent.conversationId)
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reduce(intent: MessageHistoryIntent, currentState: MessageHistoryViewState.Error) {
        when (intent) {
            is MessageHistoryIntent.ReloadScreen -> reloadScreen()
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reduce(
        intent: MessageHistoryIntent,
        currentState: MessageHistoryViewState.Display
    ) {
        when (intent) {
            is MessageHistoryIntent.EnterScreen -> enterScreen(intent.conversationId)
            is MessageHistoryIntent.UpdateYourMessageText -> updateYourMessageText(
                intent.text,
                currentState
            )
            is MessageHistoryIntent.IncreaseMessageList -> increaseMessageList(intent.currentListSize, currentState)
            is MessageHistoryIntent.SendMessage -> sendMessage(currentState)
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun enterScreen(conversationId: Int) {
        try {
            viewModelScope.launch {
                val conversation =
                    conversationRepository.getConversationsByIds("$conversationId")[0]
                val topBarSubtitle = when (conversation.properties.type) {
                    ConversationType.User -> defineLastSeenDate(conversationId)
                    ConversationType.Group -> "группа"
                    ConversationType.Chat -> "${conversation.memberCount} участников"
                }
                val messages = messageRepository.getMessageList(conversationId, DefaultMessageCount)
                _viewState.value = MessageHistoryViewState.Display(
                    conversation = conversation,
                    topBarSubtitle = topBarSubtitle,
                    messages = messages
                )
            }
        } catch (e: Exception) {
            Log.e(Tag, e.toString())
            _viewState.value = MessageHistoryViewState.Error
        }
    }

    private fun reloadScreen() {
        _viewState.value = MessageHistoryViewState.Loading
    }

    private suspend fun defineLastSeenDate(userId: Int): String {
        val lastActivity = messageRepository.getLastActivity(userId)
        return if (lastActivity.online) {
            "онлайн"
        } else {
            val currentDate = Calendar.getInstance()
            val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
            val currentMount = currentDate.get(Calendar.MONTH)
            val currentYear = currentDate.get(Calendar.YEAR)

            val lastActivityDate = Calendar.getInstance()
            lastActivityDate.time = lastActivity.time
            val lastActivityDay = lastActivityDate.get(Calendar.DAY_OF_MONTH)
            val lastActivityMount = lastActivityDate.get(Calendar.MONTH)
            val lastActivityYear = lastActivityDate.get(Calendar.YEAR)

            val dateFormat = SimpleDateFormat("k:mm", Locale.getDefault())
            if (
                currentDay == lastActivityDay
                && currentMount == lastActivityMount
                && currentYear == lastActivityYear
            ) {
                "был(а) в ${dateFormat.format(lastActivityDate.time)}"
            } else {
                val dayOfMouthDateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
                "был(а) ${dayOfMouthDateFormat.format(lastActivityDate.time)} в ${dateFormat.format(lastActivityDate.time)}"
            }
        }
    }

    private fun updateYourMessageText(text: String, currentState: MessageHistoryViewState.Display) {
        _viewState.value = currentState.copy(yourMessageText = text)
    }

    private fun increaseMessageList(currentSize: Int, currentState: MessageHistoryViewState.Display) {
        try {
            viewModelScope.launch {
                val conversationId = currentState.conversation?.properties?.id ?: return@launch
                val additionalMessages = messageRepository.getMessageList(
                    conversationId = conversationId,
                    count = DefaultMessageCount,
                    offset = currentSize
                )
                val messages = currentState.messages.toMutableList()
                messages.addAll(additionalMessages)
                _viewState.value = currentState.copy(messages = messages)
            }
        } catch (e: Exception) {
            Log.e(Tag, e.toString())
            _viewState.value = MessageHistoryViewState.Error
        }
    }

    private fun sendMessage(currentState: MessageHistoryViewState.Display) {
        try {
            viewModelScope.launch {
                val yourMessageText = currentState.yourMessageText
                if (yourMessageText.isBlank()) return@launch
                val conversationId = currentState.conversation?.properties?.id ?: return@launch
                messageRepository.sendMessage(conversationId, yourMessageText)

                _viewState.value = currentState.copy(yourMessageText = "")
            }
        } catch (e: Exception) {
            Log.e(Tag, e.toString())
            _viewState.value = MessageHistoryViewState.Error
        }
    }

    companion object {
        private const val Tag = "MessageHistory"

        // Max = 200
        private const val DefaultMessageCount = 40
    }
}