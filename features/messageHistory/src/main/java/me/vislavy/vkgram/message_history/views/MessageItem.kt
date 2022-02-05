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
import coil.transform.RoundedCornersTransformation
import me.vislavy.vkgram.api.data.AttachmentType
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.api.data.Message
import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.api.data.Sticker
import java.util.*

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    model: Message,
    isLastBefore: Boolean = false,
    isLastAfter: Boolean = true
) {
    val photos = mutableListOf<Photo>()
    var sticker: Sticker? = null
    if (model.attachments.isNotEmpty()) {
        model.attachments.forEach { attachment ->
            when (attachment.type) {
                AttachmentType.PHOTO -> photos.add(attachment.photo!!)
                AttachmentType.STICKER -> sticker = attachment.sticker
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
                    model.out -> VKgramTheme.palette.secondary
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
                    when (photos.size) {
                        1 -> {
                            val photo = photos[0].sizes.find { it.type == "q" }!!
                            Image(
                                modifier = Modifier
                                    .width(photo.width.dp)
                                    .height(photo.height.dp)
                                    .padding(4.dp),
                                painter = rememberImagePainter(
                                    data = photo.url,
                                    builder = {
                                        crossfade(true)
                                        transformations(RoundedCornersTransformation(14F))
                                    }
                                ),
                                contentDescription = null,
                            )
                        }
                        2 -> Row(Modifier.padding(4.dp)) {
                            val photo1 = photos[0].sizes.find { it.type == "q" }!!
                            val photo2 = photos[1].sizes.find { it.type == "q" }!!
                            val maxHeight = if (photo1.height > photo2.height)
                                photo1.height else photo2.height

                            Image(
                                modifier = Modifier
                                    .height(maxHeight.dp)
                                    .weight(1F),
                                painter = rememberImagePainter(
                                    data = photo1.url,
                                    builder = {
                                        crossfade(true)
                                        transformations(
                                            RoundedCornersTransformation(
                                                topLeft = 10F,
                                                topRight = 5F,
                                                bottomLeft = 10F,
                                                bottomRight = 5F
                                            )
                                        )
                                    }
                                ),
                                contentDescription = null,
                            )

                            Spacer(Modifier.width(4.dp))

                            Image(
                                modifier = Modifier
                                    .height(maxHeight.dp)
                                    .weight(1F),
                                painter = rememberImagePainter(
                                    data = photo2.url,
                                    builder = {
                                        crossfade(true)
                                        transformations(
                                            RoundedCornersTransformation(
                                                topLeft = 5F,
                                                topRight = 10F,
                                                bottomLeft = 5F,
                                                bottomRight = 10F
                                            )
                                        )
                                    }
                                ),
                                contentDescription = null,
                            )
                        }
                        in 2..4 -> Row(Modifier.padding(4.dp)) {
                            val firstPhoto = photos[0].sizes.find { it.type == "q" }!!
                            Image(
                                modifier = Modifier
                                    .height(firstPhoto.height.dp)
                                    .weight(2F),
                                painter = rememberImagePainter(
                                    data = firstPhoto.url,
                                    builder = {
                                        crossfade(true)
                                        transformations(
                                            RoundedCornersTransformation(
                                                topLeft = 10F,
                                                topRight = 5F,
                                                bottomLeft = 10F,
                                                bottomRight = 5F
                                            )
                                        )
                                    }
                                ),
                                contentDescription = null,
                            )

                            Spacer(Modifier.width(4.dp))

                            Column(
                                modifier = Modifier
                                    .height(firstPhoto.height.dp)
                                    .weight(1F)
                            ) {
                                for (index in 2..photos.size) {
                                    val photo = photos[index - 1].sizes.find { it.type == "q" }!!
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1F),
                                        painter = rememberImagePainter(
                                            data = photo.url,
                                            builder = {
                                                crossfade(true)
                                                transformations(
                                                    RoundedCornersTransformation(
                                                        topLeft = 21F,
                                                        topRight = if (index == 2) 42F else 21F,
                                                        bottomLeft = 21F,
                                                        bottomRight = if (index == photos.size) 42F else 21F
                                                    )
                                                )
                                            }
                                        ),
                                        contentDescription = null,
                                    )

                                    if (index < photos.size) {
                                        Spacer(Modifier.height(4.dp))
                                    }
                                }
                            }
                        }
                        5 -> Column(
                            Modifier
                                .padding(4.dp)
                                .height(250.dp)
                        ) {
                            Row(Modifier.weight(1F)) {
                                val photo1 = photos[0].sizes.find { it.type == "q" }!!
                                Image(
                                    modifier = Modifier
                                        .height(photo1.height.dp)
                                        .weight(1F),
                                    painter = rememberImagePainter(
                                        data = photo1.url,
                                        builder = {
                                            crossfade(true)
                                            transformations(
                                                RoundedCornersTransformation(
                                                    topLeft = 24F,
                                                    topRight = 12F,
                                                    bottomLeft = 12F,
                                                    bottomRight = 12F
                                                )
                                            )
                                        }
                                    ),
                                    contentDescription = null,
                                )

                                Spacer(Modifier.width(4.dp))

                                val photo2 = photos[1].sizes.find { it.type == "q" }!!
                                Image(
                                    modifier = Modifier
                                        .height(photo1.height.dp)
                                        .weight(1F),
                                    painter = rememberImagePainter(
                                        data = photo2.url,
                                        builder = {
                                            crossfade(true)
                                            transformations(
                                                RoundedCornersTransformation(
                                                    topLeft = 12F,
                                                    topRight = 24F,
                                                    bottomLeft = 12F,
                                                    bottomRight = 12F
                                                )
                                            )
                                        }
                                    ),
                                    contentDescription = null,
                                )
                            }

                            Spacer(Modifier.height(4.dp))

                            Row(Modifier.weight(1F)) {
                                val photo3 = photos[2].sizes.find { it.type == "q" }!!
                                Image(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1.5F),
                                    painter = rememberImagePainter(
                                        data = photo3.url,
                                        builder = {
                                            crossfade(true)
                                            transformations(
                                                RoundedCornersTransformation(
                                                    topLeft = 12F,
                                                    topRight = 12F,
                                                    bottomLeft = 24F,
                                                    bottomRight = 12F
                                                )
                                            )
                                        }
                                    ),
                                    contentDescription = null,
                                )

                                Spacer(Modifier.width(4.dp))

                                val photo4 = photos[3].sizes.find { it.type == "q" }!!
                                Image(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1F),
                                    painter = rememberImagePainter(
                                        data = photo4.url,
                                        builder = {
                                            crossfade(true)
                                            transformations(
                                                RoundedCornersTransformation(12F)
                                            )
                                        }
                                    ),
                                    contentDescription = null,
                                )

                                Spacer(Modifier.width(4.dp))

                                val photo5 = photos[4].sizes.find { it.type == "q" }!!
                                Image(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1.5F),
                                    painter = rememberImagePainter(
                                        data = photo5.url,
                                        builder = {
                                            crossfade(true)
                                            transformations(
                                                RoundedCornersTransformation(
                                                    topLeft = 12F,
                                                    topRight = 12F,
                                                    bottomLeft = 12F,
                                                    bottomRight = 24F
                                                )
                                            )
                                        }
                                    ),
                                    contentDescription = null,
                                )
                            }
                        }
                        6 -> Column(
                            modifier = Modifier
                                .height(375.dp)
                                .padding(4.dp)
                        ) {
                            var photoIndex = 0
                            for (i in 1..3) {
                                Row(Modifier.weight(1F)) {
                                    for (j in 1..2) {
                                        val photo =
                                            photos[photoIndex].sizes.find { it.type == "q" }!!
                                        Image(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(
                                                    when {
                                                        i == 1 && j == 1 -> 2F
                                                        i == 3 && j == 2 -> 2F
                                                        else -> 1F
                                                    }
                                                ),
                                            painter = rememberImagePainter(
                                                data = photo.url,
                                                builder = {
                                                    crossfade(true)
                                                    transformations(
                                                        RoundedCornersTransformation(
                                                            topLeft = if (i == 1 && j == 1) 20F else 10F,
                                                            topRight = if (i == 1 && j == 2) 20F else 10F,
                                                            bottomLeft = if (i == 3 && j == 1) 20F else 10F,
                                                            bottomRight = if (i == 3 && j == 2) 20F else 10F,
                                                        )
                                                    )
                                                }
                                            ),
                                            contentDescription = null,
                                        )

                                        if (j != 2) Spacer(Modifier.width(4.dp))

                                        photoIndex++
                                    }
                                }

                                if (i != 3) Spacer(Modifier.height(4.dp))
                            }
                        }
                        7 -> Column(
                            modifier = Modifier
                                .height(375.dp)
                                .padding(4.dp)
                        ) {
                            var photoIndex = 0
                            for (i in 1..3) {
                                Row(Modifier.weight(1F)) {
                                    val columns = if (i == 2) 3 else 2
                                    for (j in 1..columns) {
                                        val photo =
                                            photos[photoIndex].sizes.find { it.type == "q" }!!
                                        Image(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(
                                                    when {
                                                        i == 1 && j == 1 -> 2F
                                                        i == 3 && j == 2 -> 2F
                                                        else -> 1F
                                                    }
                                                ),
                                            painter = rememberImagePainter(
                                                data = photo.url,
                                                builder = {
                                                    crossfade(true)
                                                    transformations(
                                                        RoundedCornersTransformation(
                                                            topLeft = if (i == 1 && j == 1) 20F else 10F,
                                                            topRight = if (i == 1 && j == 2) 20F else 10F,
                                                            bottomLeft = if (i == 3 && j == 1) 20F else 10F,
                                                            bottomRight = if (i == 3 && j == 2) 20F else 10F,
                                                        )
                                                    )
                                                }
                                            ),
                                            contentDescription = null,
                                        )

                                        if (j != columns) Spacer(Modifier.width(4.dp))

                                        photoIndex++
                                    }
                                }

                                if (i != 3) Spacer(Modifier.height(4.dp))
                            }
                        }
                        8 -> Column(
                            modifier = Modifier
                                .height(375.dp)
                                .padding(4.dp)
                        ) {
                            var photoIndex = 0
                            for (i in 1..3) {
                                Row(Modifier.weight(1F)) {
                                    val columns = if (i == 1) 2 else 3
                                    for (j in 1..columns) {
                                        val photo =
                                            photos[photoIndex].sizes.find { it.type == "q" }!!
                                        Image(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(
                                                    when {
                                                        i == 1 && j == 1 -> 2F
                                                        i == 3 && j == 1 -> 2F
                                                        i == 3 && j == 3 -> 2F
                                                        else -> 1F
                                                    }
                                                ),
                                            painter = rememberImagePainter(
                                                data = photo.url,
                                                builder = {
                                                    crossfade(true)
                                                    transformations(
                                                        RoundedCornersTransformation(
                                                            topLeft = if (i == 1 && j == 1) 20F else 10F,
                                                            topRight = if (i == 1 && j == 2) 20F else 10F,
                                                            bottomLeft = if (i == 3 && j == 1) 20F else 10F,
                                                            bottomRight = if (i == 3 && j == 3) 20F else 10F,
                                                        )
                                                    )
                                                }
                                            ),
                                            contentDescription = null,
                                        )

                                        if (j != columns) Spacer(Modifier.width(4.dp))

                                        photoIndex++
                                    }
                                }

                                if (i != 3) Spacer(Modifier.height(4.dp))
                            }
                        }
                        9 -> Column(
                            modifier = Modifier
                                .height(375.dp)
                                .padding(4.dp)
                        ) {
                            var photoIndex = 0
                            for (i in 1..3) {
                                Row(Modifier.weight(1F)) {
                                    for (j in 1..3) {
                                        val photo =
                                            photos[photoIndex].sizes.find { it.type == "q" }!!
                                        Image(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(
                                                    when {
                                                        i == 1 && j == 1 -> 2F
                                                        i == 1 && j == 3 -> 2F
                                                        i == 3 && j == 1 -> 2F
                                                        i == 3 && j == 3 -> 2F
                                                        else -> 1F
                                                    }
                                                ),
                                            painter = rememberImagePainter(
                                                data = photo.url,
                                                builder = {
                                                    crossfade(true)
                                                    transformations(
                                                        RoundedCornersTransformation(
                                                            topLeft = if (i == 1 && j == 1) 20F else 10F,
                                                            topRight = if (i == 1 && j == 3) 20F else 10F,
                                                            bottomLeft = if (i == 3 && j == 1) 20F else 10F,
                                                            bottomRight = if (i == 3 && j == 3) 20F else 10F,
                                                        )
                                                    )
                                                }
                                            ),
                                            contentDescription = null,
                                        )

                                        if (j != 3) Spacer(Modifier.width(4.dp))

                                        photoIndex++
                                    }
                                }

                                if (i != 3) Spacer(Modifier.height(4.dp))
                            }
                        }
                        10 -> Column(
                            modifier = Modifier
                                .height(375.dp)
                                .padding(4.dp)
                        ) {
                            var photoIndex = 0
                            for (i in 1..3) {
                                Row(Modifier.weight(1F)) {
                                    val columns = if (i == 2) 3 else 2
                                    for (j in 1..columns) {
                                        val photo =
                                            photos[photoIndex].sizes.find { it.type == "q" }!!
                                        Image(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(
                                                    when {
                                                        i == 1 && j == 1 -> 2F
                                                        i == 1 && j == 3 -> 2F
                                                        i == 3 && j == 1 -> 2F
                                                        i == 3 && j == 3 -> 2F
                                                        else -> 1F
                                                    }
                                                ),
                                            painter = rememberImagePainter(
                                                data = photo.url,
                                                builder = {
                                                    crossfade(true)
                                                    transformations(
                                                        RoundedCornersTransformation(
                                                            topLeft = if (i == 1 && j == 1) 20F else 10F,
                                                            topRight = if (i == 1 && j == 3) 20F else 10F,
                                                            bottomLeft = if (i == 3 && j == 1) 20F else 10F,
                                                            bottomRight = if (i == 3 && j == 3) 20F else 10F,
                                                        )
                                                    )
                                                }
                                            ),
                                            contentDescription = null,
                                        )

                                        if (j != columns) Spacer(Modifier.width(4.dp))

                                        photoIndex++
                                    }
                                }

                                if (i != 3) Spacer(Modifier.height(4.dp))
                            }
                        }
                    }

                    if (model.text.isNotBlank()) {
                        Text(
                            text = model.text,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            ),
                            color = if (model.out) Color.White else VKgramTheme.palette.primaryText,
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
            )
        )
    }
}