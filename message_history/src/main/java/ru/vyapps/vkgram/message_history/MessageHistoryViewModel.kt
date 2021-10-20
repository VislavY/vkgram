package ru.vyapps.vkgram.message_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.repositories.ConversationRepo
import ru.vyapps.vkgram.core.repositories.UserRepo
import ru.vyapps.vkgram.message_history.repositories.GroupRepo
import ru.vyapps.vkgram.message_history.repositories.MessageRepo
import java.text.SimpleDateFormat
import java.util.*

class MessageHistoryViewModel @AssistedInject constructor(
    @Assisted private val conversationId: Int,
    @Assisted("conversationType") private val conversationType: String,
    @Assisted("accessToken") private val accessToken: String,
    private val userRepo: UserRepo,
    private val conversationRepo: ConversationRepo,
    private val groupRepo: GroupRepo,
    private val messageRepo: MessageRepo
) : ViewModel() {

    private val _photoUrl = MutableStateFlow("")
    val photoUrl = _photoUrl.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _subtitle = MutableStateFlow("")
    val subtitle = _subtitle.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    init {
        getTopBarData()
        getMessages()
    }

    fun getMessages(offset: Int = 0) {
        viewModelScope.launch {
            val receivedMessages = messageRepo.getMessages(
                accessToken = accessToken,
                conversationId = conversationId,
                count = defaultMessageCount,
                offset = offset
            )
            _messages.value = (messages.value + receivedMessages)
        }
    }

    fun sendMessage(text: String) {
        if (text.isEmpty()) return

        viewModelScope.launch {
            messageRepo.sendMessage(
                accessToken = accessToken,
                conversationId = conversationId,
                text = text
            )
        }
    }

    private fun getTopBarData() {
        viewModelScope.launch {
            when (conversationType) {
                "user" -> {
                    val receivedUser = userRepo.getUsersById(accessToken, conversationId).first()
                    _photoUrl.value = receivedUser.photo100Url
                    _title.value = "${receivedUser.firstName} ${receivedUser.lastName}"
                    _subtitle.value =
                        if (receivedUser.online == 1) {
                            "онлайн"
                        } else {
                            val lastSeenDate = receivedUser.lastSeen!!.time
                            val receivedCalendar = GregorianCalendar()
                            receivedCalendar.time = lastSeenDate
                            val currentCalendar = GregorianCalendar()
                            val timeDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val time = timeDateFormat.format(lastSeenDate)
                            if (receivedCalendar.get(Calendar.DAY_OF_WEEK) == currentCalendar.get(Calendar.DAY_OF_WEEK)) {
                                "был(а) в $time"
                            } else {
                                val dayOfMonthAndMonthDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                                val dayOfMonthAndMoth =  dayOfMonthAndMonthDateFormat.format(lastSeenDate)
                                "был(а) $dayOfMonthAndMoth в $time "
                            }
                        }
                }

                "chat" -> {
                    val receivedChat = conversationRepo.getChatById(accessToken, conversationId)
                    _photoUrl.value = receivedChat.photo100Url
                    _title.value = receivedChat.title
                    _subtitle.value = "${receivedChat.membersCount} участников"
                }

                "group" -> {
                    val receivedGroup = groupRepo.getGroupById(accessToken, conversationId)
                    _photoUrl.value = receivedGroup.photo100Url
                    _title.value = receivedGroup.name
                    _subtitle.value = "группа"
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            conversationId: Int,
            @Assisted("conversationType") conversationType: String,
            @Assisted("accessToken") accessToken: String
        ): MessageHistoryViewModel
    }

    companion object {

        private const val defaultMessageCount = 40

        fun provideFactory(
            factory: Factory,
            conversationId: Int,
            conversationType: String,
            accessToken: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(
                    conversationId = conversationId,
                    conversationType = conversationType,
                    accessToken = accessToken
                ) as T
            }
        }
    }
}