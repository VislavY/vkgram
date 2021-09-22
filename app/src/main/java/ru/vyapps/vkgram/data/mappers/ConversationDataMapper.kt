package ru.vyapps.vkgram.data.mappers

import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.vk_api.ConversationData
import javax.inject.Inject
import kotlin.math.abs

class ConversationDataMapper @Inject constructor() {

    fun map(input: ConversationData): List<Conversation> {
        val conversations = ArrayList<Conversation>()

        with(input.response) {
            items.forEach { item ->
                val conversation = with(item) {
                    Conversation(
                        id = conversation.peer.id,
                        type = conversation.peer.type,
                        lastMessage = lastMessage.text,
                        lastMessageDate = lastMessage.date
                    )
                }

                item.conversation.chatSettings?.let { chatSettings ->
                    conversation.title = chatSettings.title
                    chatSettings.photo?.let { photo ->
                        conversation.avatar = photo.photo200
                    }
                }

                when (conversation.type) {
                    "user" -> {
                        profiles.forEach { profile ->
                            if (conversation.id == profile.id) {
                                conversation.avatar = profile.photo200
                                conversation.title = "${profile.firstName} ${profile.lastName}"
                            }
                        }
                    }

                    "group" -> {
                        groups.forEach { group ->
                            if (abs(conversation.id) == group.id) {
                                conversation.avatar = group.photo200
                                conversation.title = group.name
                            }
                        }
                    }
                }

                conversations.add(conversation)
            }
        }

        return conversations
    }
}