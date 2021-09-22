package ru.vyapps.vkgram.data.mappers

import ru.vyapps.vkgram.vk_api.Message
import ru.vyapps.vkgram.vk_api.MessageData
import javax.inject.Inject

class MessageHistoryDataMapper @Inject constructor() {

    fun map(input: MessageData): List<Message> {
        return input.response.items
    }
}