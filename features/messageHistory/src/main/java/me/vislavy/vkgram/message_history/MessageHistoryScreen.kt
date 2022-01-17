package me.vislavy.vkgram.message_history

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent
import me.vislavy.vkgram.message_history.models.MessageHistoryIntent
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.message_history.views.MessageHistoryBottomBar
import me.vislavy.vkgram.message_history.views.MessageHistoryContent
import me.vislavy.vkgram.message_history.views.MessageHistoryTopBar

@ExperimentalAnimatedInsets
@Composable
fun MessageHistoryScreen(
    conversationId: Int,
    navController: NavController,
    viewModel: MessageHistoryViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val state = viewState.value) {
        is MessageHistoryViewState.Loading -> LoadingContent()
        is MessageHistoryViewState.Error -> ErrorContent(onReloadClick = {
            viewModel.onIntent(MessageHistoryIntent.ReloadScreen)
        })
        is MessageHistoryViewState.Display -> Scaffold(
            topBar = {
                MessageHistoryTopBar(
                    viewState = state,
                    navController = navController
                )
            },
            bottomBar = {
                MessageHistoryBottomBar(
                    viewState = state,
                    onTextChange = { text ->
                        viewModel.onIntent(MessageHistoryIntent.UpdateYourMessageText(text))
                    },
                    onSendClick = { viewModel.onIntent(MessageHistoryIntent.SendMessage) }
                )
            }
        ) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            MessageHistoryContent(
                modifier = modifier,
                viewState = state,
                onMessageListEnd = { currentListSize ->
                    viewModel.onIntent(MessageHistoryIntent.IncreaseMessageList(currentListSize))
                },
            )
        }
    }

    LaunchedEffect(viewState) {
        if (viewState.value !is MessageHistoryViewState.Display) {
            viewModel.onIntent(MessageHistoryIntent.EnterScreen(conversationId))
        }
    }
}