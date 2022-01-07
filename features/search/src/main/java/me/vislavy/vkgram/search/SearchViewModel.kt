package me.vislavy.vkgram.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.api.local.entities.LastConversation
import me.vislavy.vkgram.core.EventHandler
import me.vislavy.vkgram.core.repositories.ConversationRepository
import me.vislavy.vkgram.search.repositories.SearchHistoryRepository
import me.vislavy.vkgram.search.models.SearchIntent
import me.vislavy.vkgram.search.models.SearchViewState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val conversationRepository: ConversationRepository
) : ViewModel(), EventHandler<SearchIntent> {

    private val _viewState = MutableStateFlow<SearchViewState>(SearchViewState.Display())
    val viewState = _viewState.asStateFlow()

    private var lastChangeSearchTextDate = GregorianCalendar()

    override fun onEvent(event: SearchIntent) {
        when (val currentState = _viewState.value) {
            is SearchViewState.Error -> reduce(event, currentState)
            is SearchViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(intent: SearchIntent, currentState: SearchViewState.Error) {
        when (intent) {
            is SearchIntent.Reload -> reloadScreen()
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reduce(intent: SearchIntent, currentState: SearchViewState.Display) {
        when (intent) {
            is SearchIntent.LoadConversationList -> getSearchHistory(currentState)
            is SearchIntent.ClearSearchHistory -> clearSearchHistory(currentState)
            is SearchIntent.StartSearch -> updateSearchText(intent.searchText, currentState)
            is SearchIntent.AddToSearchHistory -> addToSearchHistory(intent.id)
            else -> throw NotImplementedError("Invalid $intent for state $currentState")
        }
    }

    private fun reloadScreen() {
        getSearchHistory(SearchViewState.Display())
    }

    private fun updateSearchText(searchText: String, currentState: SearchViewState.Display) {
        val newState = currentState.copy(historyPanelEnabled = searchText.isBlank())
        _viewState.value = newState

        viewModelScope.launch {
            lastChangeSearchTextDate = GregorianCalendar()

            delay(SearchCooldown.toLong())

            val currentDate = GregorianCalendar()
            currentDate.add(Calendar.MILLISECOND, -SearchCooldown)
            if (currentDate < lastChangeSearchTextDate) {
                return@launch
            }

            _viewState.value = newState.copy(isLoading = true)

            if (searchText.isBlank()) {
                getSearchHistory(newState)
            } else {
                getConversationsByName(searchText, newState)
            }
        }
    }

    private fun getSearchHistory(currentState: SearchViewState.Display) {
        viewModelScope.launch {
            try {
                val lastConversationIds = mutableListOf<Int>()
                searchHistoryRepository.getSearchHistory(DefaultLastConversationCount).forEach { lastConversation ->
                    lastConversationIds.add(lastConversation.conversationId)
                }

                val conversationModels = conversationRepository.getConversationsByIds(lastConversationIds.joinToString(","))
                _viewState.value = currentState.copy(
                    conversationModels = conversationModels,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = SearchViewState.Error
            }
        }
    }

    private fun clearSearchHistory(currentState: SearchViewState.Display) {
        viewModelScope.launch {
            try {
                searchHistoryRepository.clearSearchHistory()
                _viewState.value = currentState.copy(conversationModels = emptyList())
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = SearchViewState.Error
            }
        }
    }

    private fun getConversationsByName(name: String, currentState: SearchViewState.Display) {
        viewModelScope.launch {
            try {
                delay(500)

                val conversationModels = conversationRepository.getConversationsByName(
                    name = name,
                    count = DefaultFindConversationCount
                )
                _viewState.value = currentState.copy(
                    conversationModels = conversationModels,
                    isLoading = false
                )
            } catch (e: java.lang.Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = SearchViewState.Error
            }
        }
    }

    private fun addToSearchHistory(conversationId: Int) {
        viewModelScope.launch {
            val historyModel = LastConversation(conversationId = conversationId)
            searchHistoryRepository.addToSearchHistory(historyModel)
        }
    }

    companion object {
        const val Tag = "Search"

        // Max = 100
        const val DefaultLastConversationCount = 20

        // Max = 255
        const val DefaultFindConversationCount = 255

        // In milliseconds
        const val SearchCooldown = 500
    }
}