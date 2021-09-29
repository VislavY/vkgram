package ru.vyapps.vkgram.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import ru.vyapps.vkgram.conversations.utils.LastMessageDate
import ru.vyapps.vkgram.core.theme.BlueGrey800
import ru.vyapps.vkgram.core.theme.Cyan500
import ru.vyapps.vkgram.core.theme.Typography

@ExperimentalCoilApi
@Composable
fun ConversationsScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ConversationsViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(BlueGrey800)
    }


    Scaffold(
        content = { padding  ->
            val modifier = Modifier.padding(padding)
            ConversationListContent(
                modifier,
                navController,
                viewModel
            )
        },
        bottomBar = {
            BottomAppBar(backgroundColor = BlueGrey800) {

            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            systemUiController.setNavigationBarColor(Color.White)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ConversationListContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ConversationsViewModel = viewModel()
) {
    val conversationsState = viewModel.conversations.collectAsState(emptyList())
    with (conversationsState) {
        LazyColumn(modifier = modifier) {
            itemsIndexed(value) { index, conversation ->
                Conversation(
                    conversation,
                    modifier = Modifier.clickable {
                    navController.navigate("message_history_screen/"
                            + "${conversation.type}/"
                            + "${conversation.id}")
                    }
                )

                if (index == (value.size - 5)) {
                    viewModel.getConversations(value.size)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun Conversation(
    conversation: Conversation,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                conversation
                .properties
                .photo
                ?.photo200
            ),
            contentDescription = "Conversation image",
            modifier = Modifier
                .clip(CircleShape)
                .size(56.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Row {
                Text(
                    text = conversation.properties.title,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.subtitle1
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = LastMessageDate.timeDifference(conversation.lastMessage.date),
                    modifier = Modifier.padding(top = 5.dp),
                    maxLines = 1,
                    style = Typography.caption
                )
            }

            Spacer(Modifier.height(2.dp))

            Row {
                if (conversation.type == "chat") {
                    val lastMessagePrefix =
                        if (VK.getUserId() != conversation.lastMessage.id)
                            "${conversation.lastMessageAuthor}: " else ""
                    Text(
                        text = lastMessagePrefix,
                        color = Cyan500,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.body1
                    )
                }

                val lastMessageFontWeight =
                    if (VK.getUserId() == conversation.lastMessage.userId)
                        FontWeight.Medium else FontWeight.Normal
                Text(
                    text = conversation.lastMessage.text,
                    modifier = Modifier.padding(end = 26.dp),
                    fontWeight = lastMessageFontWeight,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.body1
                )
            }
        }
    }
}