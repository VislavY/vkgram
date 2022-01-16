package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.views.VKgramDivider
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState

@Composable
fun MessageHistoryBottomBar(
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    color: Color = VKgramTheme.palette.primary,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = color
    ) {
        Column {
            VKgramDivider()

            Row(verticalAlignment = Alignment.Bottom) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Attachment,
                        contentDescription = null,
                        modifier = Modifier.rotate(120F),
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                BasicTextField(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1F),
                    value = viewState.yourMessageText,
                    onValueChange = { text ->
                        onTextChange(text)
                    },
                    textStyle = VKgramTheme.typography.body1.copy(color = VKgramTheme.palette.primaryText),
                    maxLines = 4,
                    cursorBrush = SolidColor(VKgramTheme.palette.secondary),
                    decorationBox = { innerTextField ->
                        if (viewState.yourMessageText.isEmpty()) {
                            Text(
                                text = "Сообщение",
                                color = VKgramTheme.palette.hintText,
                                style = VKgramTheme.typography.body1
                            )
                        }

                        innerTextField()
                    }
                )

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SentimentSatisfied,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                IconButton(onClick = onSendClick) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = VKgramTheme.palette.secondary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageHistoryBottomBar_Preview() {
    MainTheme {
        MessageHistoryBottomBar(
            viewState = MessageHistoryViewState.Display(),
            onTextChange = { },
            onSendClick = { }
        )
    }
}