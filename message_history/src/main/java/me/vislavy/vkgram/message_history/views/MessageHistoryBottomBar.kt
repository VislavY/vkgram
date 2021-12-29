package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun MessageHistoryBottomBar(
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.primary,
        elevation = 4.dp
    ) {
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Attachment,
                    contentDescription = null,
                    modifier = Modifier.rotate(120f),
                    tint = VKgramTheme.palette.onSurface
                )
            }

            var messageState by remember { mutableStateOf("") }
            TextField(
                value = messageState,
                onValueChange = { text ->
                    messageState = text
                },
                modifier = Modifier.weight(1f),
                textStyle = VKgramTheme.typography.body1.copy(color = VKgramTheme.palette.primaryText),
                placeholder = {
                    Text(
                        text = "Сообщение",
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body1
                    )
                },
                maxLines = 4,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = VKgramTheme.palette.background,
                    cursorColor = VKgramTheme.palette.secondary,
                    unfocusedIndicatorColor = VKgramTheme.palette.background,
                    focusedIndicatorColor = VKgramTheme.palette.background
                )
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.SentimentSatisfied,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }

            IconButton(
                onClick = {
                    onSendClick(messageState)
                    messageState = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = VKgramTheme.palette.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun MessageHistoryBottomBar_Preview() {
    MainTheme {
        MessageHistoryBottomBar(onSendClick = { })
    }
}

@Preview
@Composable
fun DarkMessageHistoryBottomBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MessageHistoryBottomBar(onSendClick = { })
    }
}