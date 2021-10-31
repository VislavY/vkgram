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
                    tint = VKgramTheme.palette.onPrimary
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
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.subtitle1
                    )

                    val subtitle = viewModel.subtitle.collectAsState()
                    Text(
                        text = subtitle.value,
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body2
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
                    tint = VKgramTheme.palette.onPrimary
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
    val messageBackgroundColor = if (isSender) VKgramTheme.palette.secondary else VKgramTheme.palette.surface
    val messageTextColor = if (isSender) Color.White else VKgramTheme.palette.primaryText
    Box(modifier.fillMaxWidth(), messageAlignment) {
        Box(Modifier.width(300.dp), messageAlignment) {
            Column(
                modifier = Modifier
                    .background(messageBackgroundColor, RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = message.text,
                    color = messageTextColor,
                    style = VKgramTheme.typography.body1
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "10:00",
                    color = messageTextColor,
                    style = VKgramTheme.typography.caption
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
                    tint = VKgramTheme.palette.onSurface
                )
            }

            var enteredTextState by remember { mutableStateOf("") }
            TextField(
                value = enteredTextState,
                onValueChange = { text ->
                    enteredTextState = text
                },
                modifier = Modifier.weight(1f),
                textStyle = VKgramTheme.typography.body1.copy(VKgramTheme.palette.primaryText),
                placeholder = {
                    Text(
                        text = "Сообщение",
                        color = Color(0xFFb0bec5),
                        style = VKgramTheme.typography.body1
                    )
                },
                maxLines = 4,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = VKgramTheme.palette.secondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.EmojiEmotions,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }

            IconButton(onClick = {
                viewModel.sendMessage(enteredTextState)
                enteredTextState = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = null,
                    tint = VKgramTheme.palette.secondary
                )
            }
        }
    }
}
