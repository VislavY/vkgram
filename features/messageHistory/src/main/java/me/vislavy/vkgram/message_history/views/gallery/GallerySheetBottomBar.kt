package me.vislavy.vkgram.message_history.views.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun GallerySheetBottomBar(
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    onTextChange: (TextFieldValue) -> Unit,
) {
    var stickersSheetVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = VKgramTheme.palette.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(verticalAlignment = Alignment.Bottom) {
                IconButton(onClick = {
                    stickersSheetVisible = !stickersSheetVisible
                }) {
                    Icon(
                        imageVector = Icons.Default.SentimentSatisfied,
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
                                text = "Комментарий",
                                color = VKgramTheme.palette.hintText,
                                style = VKgramTheme.typography.searchText
                            )
                        }

                        innerTextField()
                    }
                )
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
fun PreviewGallerySheetBottomBar() {
    MainTheme {
        GallerySheetBottomBar(
            viewState = MessageHistoryViewState.Display(),
            onTextChange = { }
        )
    }
}