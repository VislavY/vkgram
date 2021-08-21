package ru.vyapps.vkgram.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vyapps.vkgram.data.repositories.ConversationsRepository
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val repository: ConversationsRepository
) : ViewModel() {

    val conversations = repository
        .getConversations()
        .asLiveData()

    init {
        loadConversations(20)
    }

    fun loadConversations(count: Int) {
        repository.loadConversations(count)
    }
}