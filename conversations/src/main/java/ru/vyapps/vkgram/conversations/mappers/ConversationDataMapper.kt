package ru.vyapps.vkgram.conversations.mappers

import ru.vyapps.vkgram.conversations.Conversation
import ru.vyapps.vkgram.vk_api.data.ChatPhoto
import ru.vyapps.vkgram.vk_api.data.ChatSettings
import ru.vyapps.vkgram.vk_api.data.ConversationData
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
                        properties = ChatSettings(),
                        unreadMessageCount = conversation.unreadMessageCount,
                        lastReadMessageId = conversation.lastReadMessageId,
                        lastMessage = lastMessage
                    )
                }

                item.conversation.chatSettings?.let { properties ->
                    conversation.properties = properties

                    profiles.forEach { profile ->
                        if (profile.id == conversation.lastMessage.userId) {
                            conversation.lastMessageAuthor = profile.firstName
                        }
                    }
                }

                when (conversation.type) {
                    "user" -> {
                        profiles.forEach { profile ->
                            if (conversation.id == profile.id) {
                                conversation.properties.photo = ChatPhoto(
                                    profile.photo50,
                                    profile.photo100,
                                    profile.photo200
                                )
                                conversation.properties.title =
                                    "${profile.firstName} ${profile.lastName}"
                            }
                        }
                    }

                    "group" -> {
                        groups.forEach { group ->
                            if (abs(conversation.id) == group.id) {
                                conversation.properties.photo = ChatPhoto(
                                    group.photo50,
                                    group.photo100,
                                    group.photo200
                                )
                                conversation.properties.title = group.name
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