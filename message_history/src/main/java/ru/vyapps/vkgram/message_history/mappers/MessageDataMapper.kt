package ru.vyapps.vkgram.message_history.mappers

import ru.vyapps.vkgram.message_history.Message
import ru.vyapps.vkgram.vk_api.data.MessageData
import javax.inject.Inject

class MessageDataMapper @Inject constructor() {

    fun map(input: MessageData): List<Message> {
        val messages = ArrayList<Message>()
        input.response.items.forEach { message ->
            with (message) {
                messages.add(
                    Message(
                        id,
                        userId,
                        ConversationId,
                        text,
                        date,
                        out
                    )
                )
            }
        }

        return messages
    }
}