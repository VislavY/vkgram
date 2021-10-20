package ru.vyapps.vkgram.message_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.vyapps.vkgram.core.theme.*
import ru.vyapps.vkgram.core.theme.Typography

@ExperimentalCoilApi
@Composable
fun MessageHistoryScreen(
    navController: NavController = rememberNavController(),
    viewModel: MessageHistoryViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(Color.White)
    }

    Scaffold(
        topBar = {
            MessageHistoryTopBar(navController, viewModel)
        },
        bottomBar = {
            MessageHistoryBottomBar(viewModel)
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        MessageHistoryContent(modifier, viewModel)
    }
}

@ExperimentalCoilApi
@Composable
fun MessageHistoryTopBar(
    navController: NavController = rememberNavController(),
    viewModel: MessageHistoryViewModel = viewModel()
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
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIos,
                    contentDescription = null,
                    tint = BlueGrey700
                )
            }

            Spacer(Modifier.width(16.dp))

            Row {
                val photoState = viewModel.photoUrl.collectAsState()
                Image(
                    painter = rememberImagePainter(
                        data = photoState.value,
                        builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.photo_placeholder_42)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    val titleState = viewModel.title.collectAsState()
                    Text(
                        text = titleState.value,
                        color = BlueGrey900,
                        style = Typography.subtitle1
                    )

                    val subtitle = viewModel.subtitle.collectAsState()
                    Text(
                        text = subtitle.value,
                        color = BlueGrey300,
                        style = Typography.body2
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {

                }
            ) {
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
    viewModel: MessageHistoryViewModel = viewModel()
) {
    val messagesState = viewModel.messages.collectAsState()
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        with(messagesState) {
            itemsIndexed(value) { index, message ->
                MessageItem(
                    message = message,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )


                if (index == (value.size - 5)) {
                    viewModel.getMessages(value.size)
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, modifier: Modifier = Modifier) {
    val isSender = (message.out == 1)
    val messageAlignment = if (isSender) Alignment.CenterEnd else Alignment.CenterStart
    val messageBackgroundColor = if (isSender) LightBlue500 else BlueGrey50
    val messageTextColor = if (isSender) Color.White else BlueGrey900
    Box(modifier.fillMaxWidth(), messageAlignment) {
        Box(Modifier.width(300.dp), messageAlignment) {
            Box(
                modifier = Modifier
                    .background(messageBackgroundColor, RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
            ) {
                Text(
                    text = message.text,
                    color = messageTextColor,
                    style = Typography.body1
                )
            }
        }
    }
}

@Composable
fun MessageHistoryBottomBar(
    viewModel: MessageHistoryViewModel = viewModel()
) {
    Box(Modifier.shadow(8.dp)) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {

                }
            ) {
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
                textStyle = Typography.body1.copy(BlueGrey900),
                placeholder = {
                    Text(
                        text = "Сообщение",
                        color = Color(0xFFb0bec5),
                        style = Typography.body1
                    )
                },
                maxLines = 4,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = LightBlue500,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
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
                    tint = LightBlue500
                )
            }
        }
    }
}
