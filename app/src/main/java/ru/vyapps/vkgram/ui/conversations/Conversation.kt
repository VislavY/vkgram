package ru.vyapps.vkgram.ui.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ui.theme.Typography

@Composable
fun Conversation(
    title: String,
    lastMsg: String,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.default_avatar),
            contentDescription = stringResource(R.string.default_avatar_content_description),
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row {
                Text(
                    title,
                    style = Typography.subtitle2
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Text(
                        date,
                        style = Typography.caption
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                lastMsg,
                style = Typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConversation() {
    Conversation(
        "Владислав Янц",
        "Я использую VKgram!",
        "пн"
    )
}