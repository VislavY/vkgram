package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.api.data.Attachment
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.api.data.Message
import java.util.*

@Composable
fun MessageHistoryContent(
    onMessageListEnd: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    onMessageMediaClick: (Attachment) -> Unit
) {
    val listState = rememberLazyListState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        LazyColumn(
//            modifier = Modifier.nestedScroll(connection = rememberImeNestedScrollConnection()),
            state = listState,
            reverseLayout = true,
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Spacer(Modifier.height(16.dp))
            }

            itemsIndexed(viewState.messages) { index, model ->
                val visibleItem = listState.layoutInfo.visibleItemsInfo.find { it.index == index }
                var isLastBefore = false
                if (index > 0) {
                    val previousModel = viewState.messages[index - 1]
                    isLastBefore = (model.out != previousModel.out)
                }
                var isLastAfter = false
                if (index < (viewState.messages.size - 1)) {
                    val nextModel = viewState.messages[index + 1]
                    isLastAfter = (model.out != nextModel.out)
                }
                MessageItem(
                    modifier = Modifier,
                    model = model,
                    isLastBefore = isLastBefore,
                    isLastAfter = isLastAfter,
                    offsetInList = visibleItem?.offset ?: 0,
                    onMediaClick = onMessageMediaClick
                )

                if (index == (viewState.messages.size - 1)) {
                    onMessageListEnd(viewState.messages.size)
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageHistoryContent_Preview() {
    MainTheme {
        MessageHistoryContent(
            viewState = MessageHistoryViewState.Display(
                messages = listOf(
                    Message(
                        id = 1,
                        userId = 1,
                        ConversationId = 1,
                        text = "Sample text",
                        attachments = emptyList(),
                        date = Date(),
                        out = false
                    ),
                    Message(
                        id = 2,
                        userId = 2,
                        ConversationId = 2,
                        text = "Sample text 2",
                        attachments = emptyList(),
                        date = Date(),
                        out = true
                    )
                )
            ),
            onMessageListEnd = { },
            onMessageMediaClick = { }
        )
    }
}