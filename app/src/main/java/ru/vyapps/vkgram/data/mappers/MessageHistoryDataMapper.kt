package ru.vyapps.vkgram.data.mappers

import ru.vyapps.vkgram.data.remote.Message
import ru.vyapps.vkgram.data.remote.MessageHistoryData
import javax.inject.Inject

class MessageHistoryDataMapper @Inject constructor() {

    fun map(input: MessageHistoryData): List<Message> {
        return input.response.items
    }
}