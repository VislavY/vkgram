package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.models.MessageHistoryContentState
import me.vislavy.vkgram.api.data.Message
import java.util.*

@Composable
fun MessageHistoryContent(
    onMessageListEnd: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewState: MessageHistoryContentState.Display
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
            itemsIndexed(viewState.messages) { i, message ->
                MessageItem(model = message)

                if (i == (viewState.messages.size - 1)) {
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
            viewState = MessageHistoryContentState.Display(
                messages = listOf(
                    Message(
                        id = 1,
                        userId = 1,
                        ConversationId = 1,
                        text = "Sample text",
                        attachments = emptyList(),
                        date = Date(),
                        out = 0
                    ),
                    Message(
                        id = 2,
                        userId = 2,
                        ConversationId = 2,
                        text = "Sample text 2",
                        attachments = emptyList(),
                        date = Date(),
                        out = 1
                    )
                )
            ),
            onMessageListEnd = { }
        )
    }
}

@Preview
@Composable
fun DarkMessageHistoryContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MessageHistoryContent(
            viewState = MessageHistoryContentState.Display(
                messages = listOf(
                    Message(
                        id = 1,
                        userId = 1,
                        ConversationId = 1,
                        text = "Sample text",
                        attachments = emptyList(),
                        date = Date(),
                        out = 0
                    ),
                    Message(
                        id = 2,
                        userId = 2,
                        ConversationId = 2,
                        text = "Sample text 2",
                        attachments = emptyList(),
                        date = Date(),
                        out = 1
                    )
                )
            ),
            onMessageListEnd = { }
        )
    }
}