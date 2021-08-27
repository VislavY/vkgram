package ru.vyapps.vkgram.ui.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
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
            MessageHistoryTopBar()
        }
    ) {

    }
}


@ExperimentalCoilApi
@Composable
fun MessageHistoryTopBar(
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
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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
                            painter = rememberImagePainter(user.photo_50),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp),
                            alignment = Alignment.Center
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "${user.first_name} ${user.last_name}",
                                style = Typography.subtitle1
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
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = BlueGrey700
                )
            }
        }
    }
}

//@Composable
//fun MessageHistoryContent(
//    conversationId: Long,
//    modifier: Modifier = Modifier,
//    viewModel: MessagesViewModel = viewModel()
//) {
//    val newMessages = viewModel.messages.collectAsState(ArrayList())
//    val messages by remember { mutableStateOf(ArrayList<Message>()) }
//    messages.addAll(newMessages.value)
//
//    val newMsg = viewModel.newMsg.collectAsState(null)
//    newMsg.value?.let { message ->
//        messages.add(0, message)
//    }
//
//    LazyColumn(
//        modifier = modifier,
//        reverseLayout = true
//    ) {
//        itemsIndexed(messages) { index, message ->
//            val messageHorizontalAlignment = if (message.out == 1) {
//                Alignment.End
//            } else {
//                Alignment.Start
//            }
//
//            Message(
//                text = message.text,
//                horizontalAlignment= messageHorizontalAlignment
//            )
//
//            if (index >= messages.size - 1) {
//                viewModel.loadMessages(conversationId, messages.size)
//            }
//        }
//    }
//}

@Composable
fun Message(
    text: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
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
                        color = BlueGrey50,
                        shape = Shapes.small.copy(CornerSize(12.dp))
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = text,
                    color = BlueGrey700,
                    style = Typography.body2
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}

//@Preview("Message")
//@Composable
//fun MessagePreview() {
//    Message("Привет!")
//}

@Composable
fun MessagesBottomBar(viewModel: MessagesViewModel = viewModel()) {

//    Row(modifier = Modifier.shadow(8.dp)) {
//        var enteredText by remember { mutableStateOf("") }
//        TextField(
//            value = enteredText,
//            onValueChange = { enteredText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//            placeholder = {
//                Text(
//                    text = "Сообщение",
//                    style = Typography.body1
//                )
//            },
//            maxLines = 4,
//            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
//        )
//
//        IconButton(onClick = {
//            viewModel.sendMessage(enteredText)
//            enteredText = ""
//            },
//            modifier = Modifier.background(Color.White)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Send,
//                contentDescription = null,
//                tint = Cyan500
//            )
//        }
}

//@Preview("Bottom Bar")
//@Composable
//fun MessagesBottomBarPreview() {
//    MessagesBottomBar()
//}