package ru.vyapps.vkgram.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.vyapps.vkgram.data.Conversation

interface ConversationsRepository {

    fun getConversations(): Flow<List<Conversation>>

    fun loadConversations(count: Int)
}