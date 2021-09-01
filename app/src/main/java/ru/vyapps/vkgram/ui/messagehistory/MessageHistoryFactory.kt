package ru.vyapps.vkgram.ui.messagehistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessageHistoryFactory(
    private val conversationId: Long
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageHistoryViewModel(conversationId) as T
    }
}