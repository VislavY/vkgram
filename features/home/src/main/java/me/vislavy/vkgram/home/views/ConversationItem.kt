package me.vislavy.vkgram.home.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.home.utils.LastMessageDate
import me.vislavy.vkgram.home.R
import me.vislavy.vkgram.api.AttachmentType
import me.vislavy.vkgram.api.data.Message
import java.util.*

@ExperimentalAnimationApi
@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    model: ConversationModel,
    onClick: (ConversationModel) -> Unit,
    color: Color = VKgramTheme.palette.surface,
    contentPadding: PaddingValues = PaddingValues(16.dp, 8.dp)
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(model) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    modifier = Modifier.size(56.dp),
                    painter = rememberImagePainter(
                        data = model.photo,
                        builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.photo_placeholder_56)
                        }
                    ),
                    contentDescription = stringResource(R.string.conversation_photo_content_desc)
                )

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    visible = model.indicatorEnabled,
                    enter = scaleIn(tween(200)),
                    exit = scaleOut(tween(200))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.secondary,
                                shape = CircleShape
                            )
                            .size(16.dp)
                            .border(
                                width = 2.dp,
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1F),
                        text = model.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.subtitle1
                    )

                    Spacer(Modifier.width(16.dp))

                    if (model.lastMessage!!.out) {
                        Icon(
                            modifier = Modifier.size(17.dp),
                            imageVector = if (model.lastReadMessageId != model.lastMessage!!.id)
                                Icons.Default.Done else Icons.Default.DoneAll,
                            contentDescription = null,
                            tint = VKgramTheme.palette.secondary
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = LastMessageDate.timeDifference(model.lastMessage!!.date),
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.caption
                    )
                }

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (model.lastMessage!!.attachments.isNotEmpty()) {
                        val attachmentType =
                            if (model.lastMessage!!.attachments.size > 1) {
                                stringResource(R.string.album)
                            } else {
                                when (model.lastMessage!!.attachments[0].type) {
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
                                }
                            }
                        val suffix = if (model.lastMessage!!.text.isBlank()) "" else ", "
                        Text(
                            text = (attachmentType + suffix),
                            color = VKgramTheme.palette.secondary,
                            style = VKgramTheme.typography.body1
                        )
                    }

                    val prefix = if (model.lastMessage!!.out) "Вы: " else ""
                    Text(
                        modifier = Modifier.weight(1F),
                        text = (prefix + model.lastMessage!!.text),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body1
                    )

                    Spacer(Modifier.width(16.dp))

                    if (model.unreadMessageCount > 0) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = VKgramTheme.palette.secondary,
                                    shape = CircleShape
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(7.dp, 2.dp),
                                text = "${model.unreadMessageCount}",
                                color = VKgramTheme.palette.onSecondary,
                                style = VKgramTheme.typography.caption.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview()
@Composable
fun PreviewConversationItem() {
    MainTheme {
        ConversationItem(
            model = ConversationModel(
                id = 1,
                type = "user",
                title = "Sample title",
                unreadMessageCount = 2,
                lastMessage = Message(
                    id = 1,
                    userId = 1,
                    ConversationId = 1,
                    text = "Sample message",
                    attachments = emptyList(),
                    date = Date(),
                    out = true
                ),
                lastReadMessageId = 0,
                indicatorEnabled = true
            ),
            onClick = { }
        )
    }
}