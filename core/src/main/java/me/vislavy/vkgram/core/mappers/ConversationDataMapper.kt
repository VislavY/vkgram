package me.vislavy.vkgram.core.mappers

import me.vislavy.vkgram.api.data.ConversationByIdResponse
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.api.data.ConversationData
import javax.inject.Inject
import kotlin.math.abs

class ConversationDataMapper @Inject constructor() {

    fun map(input: ConversationData): List<ConversationModel> {
        val conversations = mutableListOf<ConversationModel>()

        input.items.forEach { conversationData ->
            var conversation = with(conversationData) {
                ConversationModel(
                    id = conversation.peer.id,
                    type = conversation.peer.type,
                    unreadMessageCount = conversation.unreadMessageCount,
                    lastReadMessageId = conversation.lastReadMessageId,
                    lastMessage = lastMessage
                )
            }

            when (conversation.type) {
                "user" -> {
                    for (user in input.profiles) {
                        if (user.id == conversation.lastMessage?.userId) {
                            conversation = conversation.copy(lastMessageAuthor = user.firstName)
                        }

                        if (user.id != conversation.id) continue

                        conversation = conversation.copy(
                            title = "${user.firstName} ${user.lastName}",
                            photo = user.photo,
                            indicatorEnabled = (user.online == 1)
                        )
                        break
                    }
                }
                "group" -> {
                    for (group in input.groups) {
                        if (group.id != abs(conversation.id)) continue

                        conversation = conversation.copy(
                            title = group.name,
                            photo = group.photo
                        )
                        break
                    }
                }
                "chat" -> {
                    conversation = conversation.copy(
                        title = conversationData.conversation.chatSettings?.title ?: "",
                        photo = conversationData.conversation.chatSettings?.photo?.photo ?: "",
                        userCount = conversationData.conversation.chatSettings?.activeIds?.size ?: 0
                    )
                }
            }

            conversations.add(conversation)
        }


        return conversations
    }

    fun map(input: ConversationByIdResponse): List<ConversationModel> {
        val conversationModels = mutableListOf<ConversationModel>()

        input.items.forEach { conversationModel ->
            var conversation = with(conversationModel) {
                ConversationModel(
                    id = peer.id,
                    type = peer.type,
                    unreadMessageCount = unreadMessageCount,
                    lastReadMessageId = lastReadMessageId,
                )
            }

            when (conversation.type) {
                "user" -> {
                    for (user in input.profiles) {
                        if (user.id == conversation.lastMessage?.userId) {
                            conversation = conversation.copy(lastMessageAuthor = user.firstName)
                        }

                        if (user.id != conversation.id) continue

                        conversation = conversation.copy(
                            title = "${user.firstName} ${user.lastName}",
                            photo = user.photo,
                            indicatorEnabled = (user.online == 1)
                        )
                        break
                    }
                }
                "group" -> {
                    for (group in input.groups) {
                        if (group.id != abs(conversation.id)) continue

                        conversation = conversation.copy(
                            title = group.name,
                            photo = group.photo
                        )
                        break
                    }
                }
                "chat" -> {
                    conversation = conversation.copy(
                        title = conversationModel.chatSettings?.title ?: "",
                        photo = conversationModel.chatSettings?.photo?.photo ?: "",
                        userCount = conversationModel.chatSettings?.activeIds?.size ?: 0
                    )
                }
            }

            conversationModels.add(conversation)
        }

        return conversationModels
    }
}