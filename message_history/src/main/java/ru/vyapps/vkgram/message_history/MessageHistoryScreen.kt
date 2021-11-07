package ru.vyapps.vkgram.message_history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.core.views.ErrorContent
import ru.vyapps.vkgram.core.views.LoadingContent
import ru.vyapps.vkgram.message_history.models.MessageHistoryContentState
import ru.vyapps.vkgram.message_history.models.MessageHistoryEvent
import ru.vyapps.vkgram.message_history.views.MessageHistoryBottomBar
import ru.vyapps.vkgram.message_history.views.MessageHistoryContent
import ru.vyapps.vkgram.message_history.views.MessageHistoryTopBar

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
                    viewModel.onEvent(MessageHistoryEvent.SendMessage(text))
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
                    viewModel.onEvent(MessageHistoryEvent.ReloadScreen)
                }
            )
            is MessageHistoryContentState.Display -> MessageHistoryContent(
                onMessageListEnd = { size ->
                    viewModel.onEvent(MessageHistoryEvent.MessageListEnd(size))
                },
                modifier = modifier,
                viewState = state
            )
            else -> throw NotImplementedError("Unexpected message_history state")
        }
    }

    LaunchedEffect(contentState, topBarState) {
        if (contentState.value !is MessageHistoryContentState.Display) {
            viewModel.onEvent(MessageHistoryEvent.EnterScreen(conversation))
        }
    }
}