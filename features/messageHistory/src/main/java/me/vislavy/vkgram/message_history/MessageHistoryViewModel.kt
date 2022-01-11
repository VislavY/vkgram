package me.vislavy.vkgram.message_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.EventHandler
import me.vislavy.vkgram.message_history.models.MessageHistoryContentState
import me.vislavy.vkgram.message_history.models.MessageHistoryEvent
import me.vislavy.vkgram.message_history.models.MessageHistoryTopBarState
import me.vislavy.vkgram.message_history.repositories.MessageRepository
import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.data.conversation.ConversationType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessageHistoryViewModel @Inject constructor(
    private val vkAccessToken: VkAccessToken,
    private val messageRepository: MessageRepository
) : ViewModel(), EventHandler<MessageHistoryEvent> {

    private val _topBarState = MutableStateFlow(MessageHistoryTopBarState("Обновление..."))
    val topBarState = _topBarState.asStateFlow()

    private val _contentState =
        MutableStateFlow<MessageHistoryContentState>(MessageHistoryContentState.Loading)
    val contentState = _contentState.asStateFlow()

    private lateinit var conversation: ConversationModel

    override fun onEvent(event: MessageHistoryEvent) {
        when (val currentContentState = _contentState.value) {
            is MessageHistoryContentState.Loading -> reduce(event, currentContentState)
            is MessageHistoryContentState.Error -> reduce(event, currentContentState)
            is MessageHistoryContentState.Display -> reduce(event, currentContentState)
        }
    }

    private fun reduce(
        event: MessageHistoryEvent,
        currentState: MessageHistoryContentState.Loading
    ) {
        when (event) {
            is MessageHistoryEvent.EnterScreen -> enterScreen(event.conversation)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: MessageHistoryEvent, currentState: MessageHistoryContentState.Error) {
        when (event) {
            is MessageHistoryEvent.ReloadScreen -> reloadScreen()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(
        event: MessageHistoryEvent,
        currentState: MessageHistoryContentState.Display
    ) {
        when (event) {
            is MessageHistoryEvent.EnterScreen -> enterScreen(event.conversation)
            is MessageHistoryEvent.MessageListEnd -> fetchMessageListAndIncrease(
                offset = event.size,
                currentContentState = currentState
            )
            is MessageHistoryEvent.SendMessage -> sendMessage(event.text)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun enterScreen(conversation: ConversationModel) {
        this.conversation = conversation


        getTopBarSubtitle()
        fetchMessageList()
    }

    private fun reloadScreen() {
        _contentState.value = MessageHistoryContentState.Loading
    }

    private fun getTopBarSubtitle() {
        viewModelScope.launch {
            when (conversation.properties.type) {
                ConversationType.User -> getUserLastActivity()
                ConversationType.Group -> _topBarState.value = MessageHistoryTopBarState("группа")
                ConversationType.Chat -> {
                    val wordEnding = when {
                        conversation.memberCount == 1 -> ""
                        conversation.memberCount <= 4 -> "а"
                        else -> "ов"
                    }

                    _topBarState.value = MessageHistoryTopBarState("${conversation.memberCount} участник$wordEnding")
                }
            }
        }
    }

    private fun getUserLastActivity() {
        viewModelScope.launch {
            try {
                val response = messageRepository.getLastActivity(
                    accessToken = vkAccessToken.accessToken,
                    userId = conversation.properties.id
                )
                val subtitle = if (response.online == 1) {
                    "онлайн"
                } else {
                    val currentDate = Calendar.getInstance()
                    val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
                    val currentMount = currentDate.get(Calendar.MONTH)
                    val currentYear = currentDate.get(Calendar.YEAR)

                    val lastActivityDate = Calendar.getInstance()
                    lastActivityDate.time = response.time
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

                _topBarState.value = MessageHistoryTopBarState(subtitle)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
            }
        }
    }

    private fun fetchMessageList() {
        viewModelScope.launch {
            try {
                val response = messageRepository.fetchMessageList(
                    accessToken = vkAccessToken.accessToken,
                    conversationId = conversation.properties.id,
                    count = DefaultMessageCount,
                    offset = 0
                )
                _contentState.value = MessageHistoryContentState.Display(response)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _contentState.value = MessageHistoryContentState.Error
            }
        }
    }

    private fun fetchMessageListAndIncrease(
        offset: Int,
        currentContentState: MessageHistoryContentState.Display
    ) {
        viewModelScope.launch {
            try {
                val response = messageRepository.fetchMessageList(
                    accessToken = vkAccessToken.accessToken,
                    conversationId = conversation.properties.id,
                    count = DefaultMessageCount,
                    offset = offset
                )
                val newMessageList = currentContentState.messages.toMutableList()
                newMessageList.addAll(response)
                _contentState.value = currentContentState.copy(messages = newMessageList)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _contentState.value = MessageHistoryContentState.Error
            }
        }
    }

    private fun sendMessage(text: String) {
        viewModelScope.launch {
            try {
                messageRepository.sendMessage(
                    accessToken = vkAccessToken.accessToken,
                    conversationId = conversation.properties.id,
                    text = text
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
            }
        }
    }

    companion object {
        private const val Tag = "messageHistory"
        // Max - 100
        private const val DefaultMessageCount = 40
    }
}