package me.vislavy.vkgram.message_history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent
import me.vislavy.vkgram.message_history.models.MessageHistoryContentState
import me.vislavy.vkgram.message_history.models.MessageHistoryEvent
import me.vislavy.vkgram.message_history.views.MessageHistoryBottomBar
import me.vislavy.vkgram.message_history.views.MessageHistoryContent
import me.vislavy.vkgram.message_history.views.MessageHistoryTopBar

@Composable
fun MessageHistoryScreen(
    conversation: ConversationModel,
    navController: NavController,
    viewModel: MessageHistoryViewModel
) {
    val topBarState = viewModel.topBarState.collectAsState()
    val contentState = viewModel.contentState.collectAsState()

    Scaffold(
        topBar = {
            MessageHistoryTopBar(
                conversation = conversation,
                state = topBarState.value,
                navController = navController
            )
        },
        bottomBar = {
            MessageHistoryBottomBar(
                onSendClick = { text ->
                    viewModel.onIntent(MessageHistoryEvent.SendMessage(text))
                }
            )
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        when (val state = contentState.value) {
            is MessageHistoryContentState.Loading -> LoadingContent(modifier)
            is MessageHistoryContentState.Error -> ErrorContent(
                modifier = modifier,
                onReloadClick = {
                    viewModel.onIntent(MessageHistoryEvent.ReloadScreen)
                }
            )
            is MessageHistoryContentState.Display -> MessageHistoryContent(
                onMessageListEnd = { size ->
                    viewModel.onIntent(MessageHistoryEvent.MessageListEnd(size))
                },
                modifier = modifier,
                viewState = state
            )
            else -> throw NotImplementedError("Unexpected messageHistory state")
        }
    }

    LaunchedEffect(contentState, topBarState) {
        if (contentState.value !is MessageHistoryContentState.Display) {
            viewModel.onIntent(MessageHistoryEvent.EnterScreen(conversation))
        }
    }
}