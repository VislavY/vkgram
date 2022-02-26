package me.vislavy.vkgram.home.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.VolumeMute
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.home.utils.LastMessageDate
import me.vislavy.vkgram.home.R
import me.vislavy.vkgram.api.data.AttachmentType
import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.api.data.PushSettings
import me.vislavy.vkgram.api.data.SortId
import java.util.*

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    model: ConversationModel,
    onClick: (ConversationModel) -> Unit,
    onLongClick: (ConversationModel) -> Unit,
    color: Color = VKgramTheme.palette.surface,
    contentPadding: PaddingValues = PaddingValues(16.dp, 8.dp),
    isSelect: Boolean = false,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(model) },
                onLongClick = { onLongClick(model) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box() {
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

                if (model.sortId.majorId > 0) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Default.PushPin,
                            contentDescription = null,
                            tint = VKgramTheme.palette.secondary
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    visible = model.onlineIndicatorEnabled,
                    enter = scaleIn(tween(200)),
                    exit = scaleOut(tween(200))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.primaryIndicator,
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

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    visible = isSelect,
                    enter = scaleIn(tween(100)),
                    exit = scaleOut(tween(100))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.primaryIndicator,
                                shape = CircleShape
                            )
                            .size(20.dp)
                            .border(
                                width = 2.dp,
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onSecondary
                        )
                    }
                }
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier.weight(1F),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1F, false),
                            text = model.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = VKgramTheme.palette.primaryText,
                            style = VKgramTheme.typography.subtitle1
                        )

                        Spacer(Modifier.width(4.dp))

                        if (model.pushSettings.soundDisabled) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.VolumeMute,
                                contentDescription = null,
                                tint = VKgramTheme.palette.secondaryIndicator
                            )
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    model.lastMessage?.out?.let {
                        if (it) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = if (model.lastMessageLocalId != model.lastReadMessageLocalId)
                                    Icons.Default.Done else Icons.Default.DoneAll,
                                contentDescription = null,
                                tint = VKgramTheme.palette.secondary
                            )
                        }
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = LastMessageDate.timeDifference(model.lastMessage?.date),
                        color = VKgramTheme.palette.secondaryIndicator,
                        style = VKgramTheme.typography.caption
                    )
                }

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    model.lastMessage?.out?.let {
                        Text(
                            text = if (it) "Вы: " else model.lastMessageAuthor,
                            color = VKgramTheme.palette.secondaryIndicator,
                            style = VKgramTheme.typography.body1
                        )
                    }

                    model.lastMessage?.let {
                        if (it.attachments.isNotEmpty()) {
                            val attachmentType =
                                if (it.attachments.size > 1) {
                                    stringResource(R.string.album)
                                } else {
                                    when (model.lastMessage!!.attachments[0].type) {
                                        AttachmentType.Photo -> stringResource(R.string.photo)
                                        AttachmentType.Video -> stringResource(R.string.video)
                                        AttachmentType.Audio -> stringResource(R.string.audio)
                                        AttachmentType.AudioMessage -> stringResource(R.string.audio_message)
                                        AttachmentType.Call -> stringResource(R.string.call)
                                        AttachmentType.Document -> stringResource(R.string.document)
                                        AttachmentType.Link -> stringResource(R.string.link)
                                        AttachmentType.Market -> stringResource(R.string.market)
                                        AttachmentType.MarketAlbum -> stringResource(R.string.market_album)
                                        AttachmentType.VkPay -> stringResource(R.string.vk_pay)
                                        AttachmentType.Wall -> stringResource(R.string.wall)
                                        AttachmentType.WallReply -> stringResource(R.string.wall_reply)
                                        AttachmentType.Stickers -> stringResource(R.string.sticker)
                                        AttachmentType.Gift -> stringResource(R.string.gift)
                                    }
                                }
                            val suffix = if (it.text.isBlank()) "" else ", "
                            Text(
                                text = (attachmentType + suffix),
                                color = VKgramTheme.palette.secondary,
                                style = VKgramTheme.typography.body1
                            )
                        }

                        Text(
                            modifier = Modifier.weight(1F),
                            text = (it.text),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = VKgramTheme.palette.secondaryText,
                            style = VKgramTheme.typography.body1
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    if (model.unreadMessageCount > 0) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (!model.pushSettings.soundDisabled)
                                        VKgramTheme.palette.secondary else VKgramTheme.palette.secondaryIndicator,
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

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewConversationItem() {
    MainTheme {
        ConversationItem(
            model = ConversationModel(
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
                onlineIndicatorEnabled = true,
                pushSettings = PushSettings(true),
                sortId = SortId(majorId = 32)
            ),
            onClick = { },
            onLongClick = { }
        )
    }
}