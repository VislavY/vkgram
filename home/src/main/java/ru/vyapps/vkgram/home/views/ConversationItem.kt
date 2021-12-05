package ru.vyapps.vkgram.home.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.vk.api.sdk.VK
import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.core.views.ItemButton
import ru.vyapps.vkgram.home.R
import ru.vyapps.vkgram.home.utils.LastMessageDate
import ru.vyapps.vkgram.vk_api.AttachmentType
import ru.vyapps.vkgram.vk_api.data.Message
import java.util.*

@ExperimentalAnimationApi
@Composable
fun ConversationItem(
    model: ConversationModel,
    onClick: (ConversationModel) -> Unit
) {
    ItemButton(
        onClick = {
            onClick(model)
        }
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = model.photo,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.photo_placeholder_56)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = stringResource(R.string.conversation_photo_content_desc),
                modifier = Modifier.size(56.dp)
            )

            Row(Modifier.align(Alignment.BottomEnd)) {
                AnimatedVisibility(
                    visible = model.indicatorEnabled,
                    enter = scaleIn(tween(200)),
                    exit = scaleOut(tween(200))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.background,
                                shape = CircleShape
                            )
                            .size(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = VKgramTheme.palette.secondary,
                                    shape = CircleShape
                                )
                                .size(10.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = model.title,
                        color = VKgramTheme.palette.primaryText,
                        modifier = Modifier.weight(1f, false),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = VKgramTheme.typography.subtitle1
                    )

                    if (model.unreadMessageCount > 0) {
                        Spacer(Modifier.width(16.dp))

                        Text(
                            text = "${model.unreadMessageCount}",
                            modifier = Modifier
                                .background(
                                    color = VKgramTheme.palette.secondary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 8.dp),
                            color = Color.White,
                            maxLines = 1,
                            style = VKgramTheme.typography.caption
                        )
                    }
                }

                Spacer(Modifier.width(32.dp))

                Text(
                    text = LastMessageDate.timeDifference(model.lastMessage!!.date),
                    modifier = Modifier.wrapContentWidth(Alignment.End),
                    color = VKgramTheme.palette.secondaryText,
                    maxLines = 1,
                    style = VKgramTheme.typography.caption
                )
            }

            Spacer(Modifier.height(2.dp))

            Row {
                Row(Modifier.weight(1f)) {
                    if (model.type == "chat") {
                        if (VK.getUserId() != model.lastMessage?.userId) {
                            Text(
                                text = "${model.lastMessageAuthor}: ",
                                color = VKgramTheme.palette.secondary,
                                style = VKgramTheme.typography.body1
                            )
                        }
                    }

                    if (!model.lastMessage?.attachments.isNullOrEmpty()) {
                        val attachmentType =
                            if (model.lastMessage!!.attachments.size > 1) {
                                stringResource(R.string.album)
                            } else {
                                when (model.lastMessage!!.attachments.first().type) {
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

                        val suffix = if (model.lastMessage?.text.isNullOrBlank()) "" else ", "
                        Text(
                            text = (attachmentType + suffix),
                            color = VKgramTheme.palette.secondary,
                            style = VKgramTheme.typography.body1
                        )
                    }

                    Text(
                        text = model.lastMessage!!.text,
                        modifier = Modifier.padding(end = 32.dp),
                        color = VKgramTheme.palette.secondaryText,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = VKgramTheme.typography.body1
                    )
                }

                if (model.lastMessage!!.out == 1) {
                    val lastMessageIsRead = model.lastReadMessageId < model.lastMessage!!.id
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = null,
                        tint = if (lastMessageIsRead) {
                            VKgramTheme.palette.onSurface
                        } else {
                            VKgramTheme.palette.secondary
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ConversationItem_Preview() {
    MainTheme {
        ConversationItem(
            model = ConversationModel(
                id = 1,
                type = "user",
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
            onClick = { }
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun DarkConversationItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ConversationItem(
            model = ConversationModel(
                id = 1,
                type = "user",
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
            onClick = { }
        )
    }
}