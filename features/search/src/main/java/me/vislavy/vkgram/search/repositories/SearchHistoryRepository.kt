package me.vislavy.vkgram.search.repositories

import me.vislavy.vkgram.api.local.entities.LastConversation

interface SearchHistoryRepository {

    suspend fun addToSearchHistory(vararg lastConversations: LastConversation)

    suspend fun deleteFromSearchHistory(id: Int)

    suspend fun getSearchHistory(count: Int): List<LastConversation>

    suspend fun clearSearchHistory()
}