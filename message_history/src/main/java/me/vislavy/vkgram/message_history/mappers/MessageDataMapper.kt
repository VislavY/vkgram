package me.vislavy.vkgram.message_history.mappers

import me.vislavy.vkgram.message_history.Message
import me.vislavy.vkgram.api.data.MessageData
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