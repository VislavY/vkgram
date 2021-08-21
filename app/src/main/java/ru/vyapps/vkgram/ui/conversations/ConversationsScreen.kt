package ru.vyapps.vkgram.ui.conversations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConversationsScreen(
    viewModel: ConversationsViewModel = viewModel()
) {
    Scaffold(bottomBar = {
        BottomAppBar {

        }
    }) {
        val conversations = viewModel.conversations.observeAsState()
        LazyColumn {
            conversations.value?.let {
                itemsIndexed(it) {index, conversation ->
                    Conversation(
                        avatar = conversation.avatar,
                        title = conversation.title,
                        lastMsg = conversation.last_message,
                        date = conversation.last_message_date.toString()
                    )

                    if (index >= it.size - 1) {
                        viewModel.loadConversations(it.size + it.size)
                    }
                }
            }
        }
    }
}