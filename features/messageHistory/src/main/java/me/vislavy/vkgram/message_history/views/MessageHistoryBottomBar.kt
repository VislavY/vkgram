package me.vislavy.vkgram.message_history.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.message_history.views.stickers_sheet.StickersSheetContent

@Composable
fun MessageHistoryBottomBar(
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    color: Color = VKgramTheme.palette.primary,
    onTextChange: (TextFieldValue) -> Unit,
    onSendClick: () -> Unit,
    onOpenGalleryClick: () -> Unit
) {
    var stickersSheetVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = color,
        elevation = 8.dp
    ) {
        Column {
            Row(verticalAlignment = Alignment.Bottom) {
                IconButton(onClick = onOpenGalleryClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                BasicTextField(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1F),
                    value = viewState.messageText,
                    onValueChange = { value ->
                        onTextChange(value)
                    },
                    textStyle = VKgramTheme.typography.searchText.copy(color = VKgramTheme.palette.onSurface),
                    maxLines = 4,
                    cursorBrush = SolidColor(VKgramTheme.palette.primary),
                    decorationBox = { innerTextField ->
                        if (viewState.messageText.text.isEmpty()) {
                            Text(
                                text = "Сообщение",
                                color = VKgramTheme.palette.hintText,
                                style = VKgramTheme.typography.searchText
                            )
                        }

                        innerTextField()
                    }
                )

                IconButton(onClick = {
                    stickersSheetVisible = !stickersSheetVisible
                }) {
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
                        tint = VKgramTheme.palette.primary
                    )
                }
            }

            AnimatedVisibility(
                visible = stickersSheetVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                StickersSheetContent(onEmojiClick = { emoji ->
                    val cursorPos = viewState.messageText.selection.start
                    val messageText = viewState.messageText.text
                    val formattedMessageText = StringBuilder(messageText)
                        .insert(cursorPos, emoji)
                        .toString()
                    onTextChange(TextFieldValue(formattedMessageText, TextRange(cursorPos + emoji.length)))
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageHistoryBottomBar() {
    MainTheme {
        MessageHistoryBottomBar(
            viewState = MessageHistoryViewState.Display(),
            onTextChange = { },
            onSendClick = { },
            onOpenGalleryClick = { }
        )
    }
}