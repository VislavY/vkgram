package ru.vyapps.vkgram.ui.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.vyapps.vkgram.data.Message
import ru.vyapps.vkgram.ui.Destinations
import ru.vyapps.vkgram.ui.theme.*

@ExperimentalCoilApi
@Composable
fun MessagesScreen(
    navController: NavController = rememberNavController(),
    conversationType: String,
    conversationId: Long
) {
    val viewModel: MessagesViewModel = viewModel(
        factory = MessagesViewModelFactory(conversationId)
    )

    Scaffold(
        topBar = {
            MessageHistoryTopBar(navController, viewModel)
        },
        content = { padding ->
            val modifier = Modifier.padding(padding)
            MessageHistoryContent(modifier, viewModel)
        },
        bottomBar = {
            MessageHistoryBottomBar(viewModel)
        }
    )
}


@ExperimentalCoilApi
@Composable
fun MessageHistoryTopBar(
    navController: NavController = rememberNavController(),
    viewModel: MessagesViewModel = viewModel()
) {
    Box(modifier = Modifier.shadow(8.dp)) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate(Destinations.CONVERSATIONS_SCREEN)
                }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = BlueGrey700
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                val userState = viewModel.user.collectAsState(null)
                userState.value?.let { user ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberImagePainter(user.photo_100),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(42.dp),
                            alignment = Alignment.Center
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "${user.first_name} ${user.last_name}",
                                style = Typography.h6
                            )

                            Text(
                                text = "${user.id}",
                                style = Typography.caption
                            )
                        }
                    }
                }
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                    tint = BlueGrey700
                )
            }
        }
    }
}

@Composable
fun MessageHistoryContent(
    modifier: Modifier = Modifier,
    viewModel: MessagesViewModel = viewModel()
) {
    val messages by remember { mutableStateOf(ArrayList<Message>()) }

    val oldMessages = viewModel.messages.collectAsState(ArrayList())
    messages.addAll(oldMessages.value)

    val newMessage = viewModel.newMessage.collectAsState(null)
    newMessage.value?.let { message ->
        messages.add(0, message)
    }

    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        itemsIndexed(messages) { index, message ->
            Message(message)

            if (index >= messages.size - 1) {
                viewModel.loadMessages(messages.size)
            }
        }
    }
}

@Composable
fun Message(msg: Message) {
    var horizontalAlignment = Alignment.Start
    var backgroundColor = BlueGrey50
    var textColor = BlueGrey700
    if (msg.out == 1) {
        horizontalAlignment = Alignment.End
        backgroundColor = Cyan500
        textColor = Color.White
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Column(
            modifier = Modifier.width(250.dp),
            horizontalAlignment = horizontalAlignment
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = Shapes.small.copy(CornerSize(12.dp))
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = msg.text,
                    color = textColor,
                    style = Typography.body1
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun MessageHistoryBottomBar(viewModel: MessagesViewModel = viewModel()) {
    Box(modifier = Modifier.shadow(8.dp)) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Attachment,
                    contentDescription = null,
                    modifier = Modifier.rotate(300f),
                    tint = BlueGrey300
                )
            }

            var enteredTextState by remember { mutableStateOf("") }
            TextField(
                value = enteredTextState,
                onValueChange = { text ->
                    enteredTextState = text
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Сообщение",
                        style = Typography.body1
                    )
                },
                maxLines = 4,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.EmojiEmotions,
                    contentDescription = null,
                    tint = BlueGrey300
                )
            }

            IconButton(onClick = {
                viewModel.sendMessage(enteredTextState)
                enteredTextState = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = null,
                    tint = Cyan500
                )
            }
        }
    }
}