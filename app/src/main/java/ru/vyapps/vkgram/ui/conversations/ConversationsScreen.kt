package ru.vyapps.vkgram.ui.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.ui.Destinations
import ru.vyapps.vkgram.ui.theme.Typography

@Composable
fun ConversationsScreen(
    viewModel: ConversationsViewModel = viewModel(),
    navController: NavController
) {
    Scaffold(bottomBar = {
        BottomAppBar {

        }
    }) {
        val conversations = viewModel.conversations.observeAsState()
        LazyColumn {
            conversations.value?.let {
                itemsIndexed(it) {index, conversation ->
                    Conversation(
                        conversation,
                        modifier = Modifier.clickable {
                            navController.navigate("${Destinations.MESSAGE_HISTORY_SCREEN}/${conversation.type}/${conversation.id}")
                        }
                    )

                    if (index >= it.size - 1) {
                        viewModel.loadConversations(it.size + it.size)
                    }
                }
            }
        }
    }
}

@Composable
fun Conversation(conversation: Conversation, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = if (conversation.avatar.isEmpty()) {
                painterResource(R.drawable.default_avatar)
            } else {
                rememberImagePainter(conversation.avatar)
            },
            contentDescription = stringResource(R.string.default_avatar_content_description),
            modifier = Modifier
                .clip(CircleShape)
                .size(56.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row {
                Text(
                    conversation.title,
                    style = Typography.subtitle1
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        conversation.last_message_date.toString(),
                        style = Typography.caption
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                conversation.last_message,
                modifier = Modifier.padding(end = 28.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.body1
            )
        }
    }
}