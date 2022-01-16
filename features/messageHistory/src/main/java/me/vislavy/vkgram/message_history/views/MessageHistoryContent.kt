package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.api.data.Message
import java.util.*
import kotlin.math.abs

@Composable
fun MessageHistoryContent(
    onMessageListEnd: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        LazyColumn(
            reverseLayout = true,
            contentPadding = PaddingValues(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(viewState.messages) { index, model ->
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
                    model = model,
                    isLastBefore = isLastBefore,
                    isLastAfter = isLastAfter
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
            onMessageListEnd = { }
        )
    }
}