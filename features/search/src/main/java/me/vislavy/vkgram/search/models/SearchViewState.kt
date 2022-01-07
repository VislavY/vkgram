package me.vislavy.vkgram.search.models

import me.vislavy.vkgram.core.ConversationModel

sealed class SearchViewState {
    object Error : SearchViewState()
    data class Display(
        val isLoading: Boolean = true,
        val historyPanelEnabled: Boolean = true,
        val conversationModels: List<ConversationModel> = emptyList(),
    ) : SearchViewState()
}
