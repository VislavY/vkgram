package me.vislavy.vkgram.search.models

sealed class SearchIntent {
    object LoadConversationList : SearchIntent()
    object Reload : SearchIntent()
    object ClearSearchHistory : SearchIntent()
    data class StartSearch(val searchText: String) : SearchIntent()
    data class AddToSearchHistory(val id: Int) : SearchIntent()
}