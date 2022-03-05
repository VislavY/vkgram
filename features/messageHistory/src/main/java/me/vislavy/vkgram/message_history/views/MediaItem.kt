package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import me.vislavy.vkgram.api.data.Attachment
import me.vislavy.vkgram.api.data.AttachmentType

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    model: Attachment,
    shape: Shape = RectangleShape,
    onClick: () -> Unit
) {
    when (model.type) {
        AttachmentType.Photo -> ImageItem(
            modifier = modifier.clickable(onClick = onClick),
            model = model.photo!!,
            shape = shape
        )
        AttachmentType.Video -> VideoItem(
            modifier = modifier.clickable(onClick = onClick),
            model = model.video!!,
            shape = shape
        )
        else -> Unit
    }
}