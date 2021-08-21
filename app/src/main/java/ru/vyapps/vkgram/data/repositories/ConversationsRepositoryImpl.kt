package ru.vyapps.vkgram.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.remote.ConversationsService
import javax.inject.Inject

class ConversationsRepositoryImpl @Inject constructor(
    private val service: ConversationsService
) : ConversationsRepository {

    override fun getConversations(): Flow<List<Conversation>> {
        return service.conversations
    }

    override fun loadConversations(count: Int) {
        service.loadConversations(count)
    }
}