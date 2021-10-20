package ru.vyapps.vkgram.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.vk.api.sdk.VK
import ru.vyapps.vkgram.core.Conversation
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.home.utils.LastMessageDate
import ru.vyapps.vkgram.core.theme.BlueGrey300
import ru.vyapps.vkgram.core.theme.BlueGrey900
import ru.vyapps.vkgram.core.theme.LightBlue500
import ru.vyapps.vkgram.core.theme.Typography
import ru.vyapps.vkgram.vk_api.AttachmentType

@ExperimentalCoilApi
@Composable
fun ConversationsContent(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = viewModel()
) {
    val conversationsState = viewModel.conversations.collectAsState(emptyList())
    with(conversationsState) {
        LazyColumn {
            itemsIndexed(value) { index, conversation ->
                ConversationItem(
                    conversation = conversation,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                route = Destinations.MESSAGE_HISTORY_SCREEN +
                                        "/${conversation.id}" +
                                        "/${conversation.type}"
                            )
                        }
                        .padding(16.dp, 8.dp)
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
fun ConversationItem(
    conversation: Conversation,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        with(conversation) {
            Image(
                painter = rememberImagePainter(
                    data = properties.photo?.photo200,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.photo_placeholder_56)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = stringResource(R.string.conversation_photo_content_desc),
                modifier = Modifier.size(56.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = properties.title,
                            color = BlueGrey900,
                            modifier = Modifier.weight(1f, false),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = Typography.subtitle1
                        )

                        if (unreadMessageCount > 0) {
                            Spacer(Modifier.width(16.dp))

                            Text(
                                text = "$unreadMessageCount",
                                modifier = Modifier
                                    .background(LightBlue500, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 8.dp),
                                color = Color.White,
                                maxLines = 1,
                                style = Typography.caption
                            )
                        }
                    }

                    Spacer(Modifier.width(32.dp))

                    Text(
                        text = LastMessageDate.timeDifference(conversation.lastMessage.date),
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        color = BlueGrey300,
                        maxLines = 1,
                        style = Typography.caption
                    )
                }

                Spacer(Modifier.height(2.dp))

                Row {
                    Row(Modifier.weight(1f)) {
                        if (type == "chat") {
                            if (VK.getUserId() != lastMessage.userId) {
                                Text(
                                    text = "${lastMessageAuthor}: ",
                                    color = LightBlue500,
                                    style = Typography.body1
                                )
                            }
                        }

                        if (lastMessage.attachments.isNotEmpty()) {
                            val attachment =
                                if (lastMessage.attachments.size > 1) {
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
                                color = LightBlue500,
                                style = Typography.body1
                            )
                        }

                        Text(
                            text = lastMessage.text,
                            modifier = Modifier.padding(end = 32.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = Typography.body1
                        )
                    }

                    if (lastMessage.out == 1) {
                        val lastMessageIsRead = lastReadMessageId < lastMessage.id
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = null,
                            tint = if (lastMessageIsRead) BlueGrey300 else LightBlue500
                        )
                    }
                }
            }
        }
    }
}