package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.*
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import java.util.*
import kotlin.math.abs

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    model: Message,
    isLastBefore: Boolean = false,
    isLastAfter: Boolean = true,
    offsetInList: Int = 0,
    onMediaClick: (Attachment) -> Unit
) {
    val photos = mutableListOf<Photo>()
    val videos = mutableListOf<Video>()
    var sticker: Sticker? = null
    if (model.attachments.isNotEmpty()) {
        model.attachments.forEach { attachment ->
            when (attachment.type) {
                AttachmentType.Photo -> photos.add(attachment.photo!!)
                AttachmentType.Video -> videos.add(attachment.video!!)
                AttachmentType.Stickers -> sticker = attachment.sticker
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isLastBefore) 8.dp else 0.dp)
    ) {
        Box(
            modifier = Modifier
                .align(
                    alignment = if (model.out) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                )
                .width(300.dp)
        ) {
            Surface(
                modifier = modifier
                    .align(
                        alignment = if (model.out) Alignment.CenterEnd else Alignment.CenterStart
                    ),
                shape = RoundedCornerShape(
                    topStart = if (!model.out && ((!isLastAfter && !isLastBefore) || !isLastAfter))
                        0.dp else 16.dp,
                    topEnd = if (model.out && ((!isLastAfter && !isLastBefore) || !isLastAfter))
                        0.dp else 16.dp,
                    bottomStart = if (!model.out) 0.dp else 16.dp,
                    bottomEnd = if (model.out) 0.dp else 16.dp,
                ),
                color = when {
                    sticker != null -> VKgramTheme.palette.background
                    model.out -> VKgramTheme.palette.primary.copy(
                        alpha = if (offsetInList == 0)
                            1F else abs(1F - (abs(offsetInList.toFloat()) / 10000) * 3)
                    )
                    else -> VKgramTheme.palette.defaultMessage
                }
            ) {
                sticker?.let {
                    Image(
                        modifier = Modifier
                            .width(192.dp)
                            .height(192.dp),
                        painter = rememberImagePainter(
                            data = it.images.last().url,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null
                    )
                }

                Column {
                    val photoAndVideoAttachments = model.attachments.filter {
                        it.type == AttachmentType.Photo  || it.type == AttachmentType.Video
                    }
                    if (photoAndVideoAttachments.isNotEmpty()) {
                        MediaMessageContent(
                            models = photoAndVideoAttachments,
                            onMediaClick = { index, models ->
                                onMediaClick(models[index])
                            }
                        )
                    }

                    if (model.text.isNotBlank()) {
                        Text(
                            text = model.text,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            ),
                            color = if (model.out) Color.White else VKgramTheme.palette.onSurface,
                            style = VKgramTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageItem_Preview() {
    MainTheme {
        MessageItem(
            model = Message(
                id = 1,
                userId = 1,
                ConversationId = 1,
                text = "Sample text",
                attachments = emptyList(),
                date = Date(),
                out = false
            ),
            onMediaClick = { }
        )
    }
}