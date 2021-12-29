package me.vislavy.vkgram.home.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.home.models.HomeViewState
import me.vislavy.vkgram.api.data.Message
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@ExperimentalSerializationApi
@ExperimentalAnimationApi
@Composable
fun ConversationListContent(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    navController: NavController,
    onListEnd: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            Spacer(Modifier.height(16.dp))

            LazyColumn {
                itemsIndexed(viewState.conversations) { i, conversation ->
                    ConversationItem(
                        model = conversation,
                        onClick = {
                            val encodedConversation = Json.encodeToString(
                                value = conversation.copy(
                                    photo = URLEncoder.encode(
                                        conversation.photo,
                                        StandardCharsets.UTF_8.toString()
                                    ),
                                    lastMessage = null
                                )
                            )
                            navController.navigate(
                                route = Destinations.MessageHistory
                                        + "/$encodedConversation"
                            )
                        }
                    )

                    if (i == (viewState.conversations.size - 1)) {
                        onListEnd(viewState.conversations.size)
                    }
                }
            }
        }
    }
}

@ExperimentalSerializationApi
@ExperimentalAnimationApi
@Preview
@Composable
fun ConversationListContent_Preview() {
    MainTheme {
        ConversationListContent(
            viewState = HomeViewState.Display(
                conversations = listOf(
                    ConversationModel(
                        id = 1,
                        type = "user",
                        title = "It's Sample",
                        unreadMessageCount = 2,
                        lastMessage = Message(
                            id = 1,
                            userId = 1,
                            ConversationId = 1,
                            text = "Sample message",
                            attachments = emptyList(),
                            date = Date(),
                            out = 1
                        ),
                        lastReadMessageId = 0,
                    ),
                    ConversationModel(
                        id = 1,
                        type = "user",
                        title = "It's Sample 2",
                        unreadMessageCount = 2,
                        lastMessage = Message(
                            id = 1,
                            userId = 1,
                            ConversationId = 1,
                            text = "Sample message 2",
                            attachments = emptyList(),
                            date = Date(),
                            out = 1
                        ),
                        lastReadMessageId = 0,
                    ),
                ),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onListEnd = { }
        )
    }
}

@ExperimentalSerializationApi
@ExperimentalAnimationApi
@Preview
@Composable
fun DarkConversationListContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ConversationListContent(
            viewState = HomeViewState.Display(
                conversations = listOf(
                    ConversationModel(
                        id = 1,
                        type = "user",
                        title = "It's Sample",
                        unreadMessageCount = 2,
                        lastMessage = Message(
                            id = 1,
                            userId = 1,
                            ConversationId = 1,
                            text = "Sample message",
                            attachments = emptyList(),
                            date = Date(),
                            out = 1
                        ),
                        lastReadMessageId = 0,
                    ),
                    ConversationModel(
                        id = 1,
                        type = "user",
                        title = "It's Sample 2",
                        unreadMessageCount = 2,
                        lastMessage = Message(
                            id = 1,
                            userId = 1,
                            ConversationId = 1,
                            text = "Sample message 2",
                            attachments = emptyList(),
                            date = Date(),
                            out = 1
                        ),
                        lastReadMessageId = 0,
                    ),
                ),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onListEnd = { }
        )
    }
}