package ru.vyapps.vkgram.core.mappers

import ru.vyapps.vkgram.core.Conversation
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
                        id = conversation.peer.localId,
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
                                conversation.user = profile
                                conversation.properties.photo = ChatPhoto(
                                    profile.photo50Url,
                                    profile.photo100Url,
                                    profile.photo200Url
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
                                    group.photo50Url,
                                    group.photo100Url,
                                    group.photo200Url
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