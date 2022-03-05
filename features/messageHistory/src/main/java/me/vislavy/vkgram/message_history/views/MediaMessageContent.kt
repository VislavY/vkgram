package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.api.data.Attachment
import me.vislavy.vkgram.api.data.AttachmentType
import me.vislavy.vkgram.api.data.PhotoSize

@Composable
fun MediaMessageContent(
    modifier: Modifier = Modifier,
    models: List<Attachment>,
    onMediaClick: (Int, List<Attachment>) -> Unit
) {
    Box(modifier = modifier.padding(4.dp)) {
        when (models.size) {
            1 -> {
                val mediaSize = calcMediaSize(models[0])
                MediaItem(
                    modifier = Modifier
                        .width(mediaSize.width.dp)
                        .height(mediaSize.height.dp),
                    model = models[0],
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        onMediaClick(0, models)
                    }
                )
            }
            2 -> {
                val firstMediaSize = calcMediaSize(models[0])
                val secondMediaSize = calcMediaSize(models[1])
                Row(
                    modifier = Modifier.height(
                        height = if (firstMediaSize.height > secondMediaSize.height)
                            secondMediaSize.height.dp else firstMediaSize.height.dp
                    )
                ) {
                    MediaItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F),
                        model = models[0],
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 6.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 6.dp
                        ),
                        onClick = {
                            onMediaClick(0, models)
                        }
                    )

                    Spacer(Modifier.width(MediaSpacerValue.dp))

                    MediaItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F),
                        model = models[1],
                        shape = RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 12.dp,
                            bottomStart = 6.dp,
                            bottomEnd = 12.dp
                        ),
                        onClick = {
                            onMediaClick(1, models)
                        }
                    )
                }
            }
            in 3..4 -> Row(Modifier.height(MaxHeight.dp)) {
                MediaItem(
                    modifier = Modifier.weight(1.5F),
                    model = models[0],
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 6.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 6.dp
                    ),
                    onClick = {
                        onMediaClick(0, models)
                    }
                )

                Spacer(Modifier.width(MediaSpacerValue.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                ) {
                    for (index in 2..models.size) {
                        MediaItem(
                            modifier = Modifier.weight(1F),
                            model = models[index - 1],
                            shape = RoundedCornerShape(
                                topStart = 6.dp,
                                topEnd = if (index == 2) 12.dp else 6.dp,
                                bottomStart = 6.dp,
                                bottomEnd = if (index == models.size) 12.dp else 6.dp,
                            ),
                            onClick = {
                                onMediaClick(index - 1, models)
                            }
                        )

                        if (index != models.size    ) {
                            Spacer(Modifier.height(MediaSpacerValue.dp))
                        }
                    }
                }
            }
            5 -> Column(Modifier.height(MaxHeight.dp)) {
                Row(Modifier.weight(1.5F)) {
                    for (index in 0..1) {
                        MediaItem(
                            modifier = Modifier.weight(1F),
                            model = models[index],
                            shape = RoundedCornerShape(
                                topStart = if (index == 0) 12.dp else 6.dp,
                                topEnd = if (index == 1) 12.dp else 6.dp,
                                bottomStart = 6.dp,
                                bottomEnd = 6.dp,
                            ),
                            onClick = {
                                onMediaClick(index, models)
                            }
                        )

                        if (index != 1) {
                            Spacer(Modifier.width(MediaSpacerValue.dp))
                        }
                    }
                }

                Spacer(Modifier.height(MediaSpacerValue.dp))

                Row(Modifier.weight(1F)) {
                    for (index in 2..4) {
                        MediaItem(
                            modifier = Modifier.weight(
                                weight = if (index == 3) 1.5F else 1F
                            ),
                            model = models[index],
                            shape = RoundedCornerShape(
                                topStart = 6.dp,
                                topEnd = 6.dp,
                                bottomStart = if (index == 2) 12.dp else 6.dp,
                                bottomEnd = if (index == 4) 12.dp else 6.dp,
                            ),
                            onClick = {
                                onMediaClick(index, models)
                            }
                        )

                        if (index != 4) {
                            Spacer(Modifier.width(MediaSpacerValue.dp))
                        }
                    }
                }
            }
            6 -> Column(Modifier.height(MaxHeight.dp)) {
                var index = 0
                for (row in 0..2) {
                    Row(Modifier.weight(1F)) {
                        for (column in 0..1) {
                            MediaItem(
                                modifier = Modifier.weight(
                                    weight = when {
                                        row == 0 && column == 0 -> 1.5F
                                        row == 2 && column == 1 -> 1.5F
                                        else -> 1F
                                    }
                                ),
                                model = models[index],
                                shape = RoundedCornerShape(
                                    topStart = if (row == 0 && column == 0) 12.dp else 6.dp,
                                    topEnd = if (row == 0 && column == 1) 12.dp else 6.dp,
                                    bottomStart = if (row == 2 && column == 0) 12.dp else 6.dp,
                                    bottomEnd = if (row == 2 && column == 1) 12.dp else 6.dp
                                ),
                                onClick = {
                                    onMediaClick(index, models)
                                }
                            )

                            if (column != 1) {
                                Spacer(Modifier.width(MediaSpacerValue.dp))
                            }

                            index++
                        }
                    }

                    if (row != 2) {
                        Spacer(Modifier.height(MediaSpacerValue.dp))
                    }
                }
            }
            in 7..10 -> Column(Modifier.height(MaxHeight.dp)) {
                var index = 0
                for (row in 0..2) {
                    Row(Modifier.weight(1F)) {
                        val columnCount = when {
                            row == 0 && models.size in 7..10 -> 2
                            row == 1 && models.size == 9 -> 2
                            row == 1 && models.size == 10 -> 3
                            row == 2 && models.size in 8..10 -> 2
                            else -> 1
                        }
                        for (column in 0..columnCount) {
                            MediaItem(
                                modifier = Modifier.weight(
                                    weight = when {
                                        models.size == 7 && row == 1 && column == 0 -> 1.5F
                                        models.size == 7 && row == 2 && column == 1 -> 1.5F
                                        models.size == 9 && row == 0 && column == 0 -> 1.5F
                                        models.size == 9 && row == 1 && column == 1 -> 1.5F
                                        models.size == 9 && row == 2 && column == 2 -> 1.5F
                                        else -> 1F
                                    }
                                ),
                                model = models[index],
                                shape = RoundedCornerShape(
                                    topStart = if (row == 0 && column == 0) 12.dp else 6.dp,
                                    topEnd = if (row == 0 && column == columnCount) 12.dp else 6.dp,
                                    bottomStart = if (row == 2 && column == 0) 12.dp else 6.dp,
                                    bottomEnd = if (row == 2 && column == columnCount) 12.dp else 6.dp,
                                ),
                                onClick = {
                                    onMediaClick(index, models)
                                }
                            )

                            if (column != columnCount) {
                                Spacer(Modifier.width(MediaSpacerValue.dp))
                            }

                            index++
                        }
                    }

                    if (row != 2) {
                        Spacer(Modifier.height(MediaSpacerValue.dp))
                    }
                }
            }
        }
    }
}

private fun calcMediaSize(attachment: Attachment): Size {
    val photoProperties: PhotoSize = when (attachment.type) {
        AttachmentType.Photo -> attachment.photo!!.sizes.find { it.type == "q" }
        AttachmentType.Video -> attachment.video!!.image[2]
        else -> null
    } ?: return Size(0F, 0F)
    return Size(
        width = if (photoProperties.height > MaxHeight)
            photoProperties.width / (photoProperties.height / MaxHeight).toFloat() else photoProperties.width.toFloat(),
        height = if (photoProperties.height > MaxHeight) MaxHeight.toFloat() else photoProperties.height.toFloat()
    )
}

private const val MaxHeight = 350
private const val MediaSpacerValue = 3