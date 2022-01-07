package me.vislavy.vkgram.search.repositories

import me.vislavy.vkgram.api.local.database.LastConversationDao
import me.vislavy.vkgram.api.local.entities.LastConversation
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val lastConversationDao: LastConversationDao
) : SearchHistoryRepository {

    override suspend fun addToSearchHistory(vararg lastConversations: LastConversation) {
        lastConversationDao.add(lastConversations)
    }

    override suspend fun deleteFromSearchHistory(id: Int) {
        lastConversationDao.deleteById(id)
    }

    override suspend fun getSearchHistory(count: Int) = lastConversationDao.getLast(count)

    override suspend fun clearSearchHistory() {
        lastConversationDao.clear()
    }
}