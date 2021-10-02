package ru.vyapps.vkgram.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import ru.vyapps.vkgram.conversations.utils.LastMessageDate
import ru.vyapps.vkgram.core.theme.BlueGrey800
import ru.vyapps.vkgram.core.theme.Cyan500
import ru.vyapps.vkgram.core.theme.Typography
import ru.vyapps.vkgram.vk_api.AttachmentType

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
            ConversationsContent(
                modifier,
                navController,
                viewModel
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                backgroundColor = BlueGrey800,
                cutoutShape = RoundedCornerShape(50)
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(Modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
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
fun ConversationsContent(
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
    with (conversation) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    properties.photo?.photo200,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.photo_placeholder)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = stringResource(R.string.conversation_photo_content_desc),
                modifier = Modifier
                    .size(56.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Row {
                    Text(
                        text = properties.title,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = Typography.subtitle1
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = LastMessageDate.timeDifference(lastMessage.date),
                        modifier = Modifier.padding(top = 5.dp),
                        maxLines = 1,
                        style = Typography.caption
                    )
                }

                Spacer(Modifier.height(2.dp))

                Row {
                    if (type == "chat") {
                        if (VK.getUserId() != lastMessage.userId) {
                            Text(
                                text = "${lastMessageAuthor}: ",
                                color = Cyan500,
                                style = Typography.body1
                            )
                        }
                    }

                    if (lastMessage.attachments.isNotEmpty()) {
                        val attachment =
                            if (lastMessage.attachments.size > 2) {
                                stringResource(R.string.album)
                            } else {
                                when (lastMessage.attachments.first().type) {
                                    AttachmentType.PHOTO -> stringResource(R.string.photo)
                                    AttachmentType.VIDEO -> stringResource(R.string.video)
                                    AttachmentType.AUDIO -> stringResource(R.string.audio)
                                    AttachmentType.AUDIO_MESSAGE -> stringResource(R.string.audio_message)
                                    AttachmentType.CALL -> stringResource(R.string.call)
                                    AttachmentType.DOCUMENT -> stringResource(R.string.document)
                                    AttachmentType.LINK -> stringResource(R.string.link)
                                    AttachmentType.MARKET -> stringResource(R.string.market)
                                    AttachmentType.MARKET_ALBUM -> stringResource(R.string.market_album)
                                    AttachmentType.VKPAY -> stringResource(R.string.vk_pay)
                                    AttachmentType.WALL -> stringResource(R.string.wall)
                                    AttachmentType.WALL_REPLY -> stringResource(R.string.wall_reply)
                                    AttachmentType.STICKER -> stringResource(R.string.sticker)
                                    AttachmentType.GIFT -> stringResource(R.string.gift)
                                    else -> ""
                                }
                            }

                        val suffix = if (lastMessage.text.isBlank()) "" else ", "
                        Text(
                            text = (attachment + suffix),
                            color = Cyan500,
                            style = Typography.body1
                        )
                    }

                    val lastMessageFontWeight =
                        if (VK.getUserId() == lastMessage.userId)
                            FontWeight.Medium else FontWeight.Normal
                    Text(
                        text = lastMessage.text,
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
}