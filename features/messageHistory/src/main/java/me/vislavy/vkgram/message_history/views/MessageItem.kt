package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.api.data.Message
import java.util.*

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    model: Message
) {
    Box(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(
                    alignment = if (model.out == 0) {
                        Alignment.CenterStart
                    } else {
                        Alignment.CenterEnd
                    }
                )
                .width(300.dp)
        ) {
            Surface(
                modifier = modifier.align(
                    alignment = if (model.out == 0) {
                        Alignment.CenterStart
                    } else {
                        Alignment.CenterEnd
                    }
                ),
                shape = RoundedCornerShape(12.dp),
                color = if (model.out == 0) {
                    VKgramTheme.palette.surface
                } else {
                    VKgramTheme.palette.secondary
                }
            ) {
                Text(
                    text = model.text,
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 8.dp
                    ),
                    color = if (model.out == 0) {
                        VKgramTheme.palette.primaryText
                    } else {
                        Color.White
                    },
                    style = VKgramTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun MessageItem_Preview() {
    MainTheme {
        MessageItem(
            model = Message(
                id = 1,
                userId = 1,
                ConversationId = 1,
                text = "Sample text",
                attachments = emptyList(),
                date = Date(),
                out = 0
            )
        )
    }
}

@Preview
@Composable
fun DarkMessageItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MessageItem(
            model = Message(
                id = 1,
                userId = 1,
                ConversationId = 1,
                text = "Sample text",
                attachments = emptyList(),
                date = Date(),
                out = 0
            )
        )
    }
}
