package ru.vyapps.vkgram.ui.conversationlist

import  androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.ui.Destinations
import ru.vyapps.vkgram.ui.theme.BlueGrey800
import ru.vyapps.vkgram.ui.theme.Typography
import ru.vyapps.vkgram.utils.LastMessageDate

@ExperimentalCoilApi
@Composable
fun ConversationListScreen(
    navController: NavController,
    viewModel: ConversationListViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(BlueGrey800)
    }

    Scaffold(
        content = { padding  ->
            val modifier = Modifier.padding(padding)
            ConversationListContent(modifier, navController, viewModel)
        },
        bottomBar = {
            BottomAppBar {

            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun ConversationListContent(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: ConversationListViewModel = viewModel()
) {
    val conversations = remember { mutableStateListOf<Conversation>() }
    val loadedConversations = viewModel.loadedConversations.collectAsState(ArrayList())
    conversations.addAll(loadedConversations.value)
    LazyColumn(modifier = modifier) {
        itemsIndexed(conversations) {index, conversation ->
            Conversation(
                conversation,
                modifier = Modifier.clickable {
                    navController.navigate("${Destinations.MESSAGE_HISTORY_SCREEN}/"
                            + "${conversation.type}/"
                            + "${conversation.id}")
                }
            )

            if (index == (conversations.size - 5)) {
                viewModel.getConversations(viewModel.loadedConversationsCount, conversations.size)
            }
        }
    }
}

@ExperimentalCoilApi
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

        Spacer(Modifier.width(16.dp))

        Column {
            Row {
                Text(
                    text = conversation.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.subtitle1
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = LastMessageDate.timeDifference(conversation.lastMessageDate),
                        style = Typography.caption
                    )
                }
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = conversation.lastMessage,
                modifier = Modifier.padding(end = 28.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}