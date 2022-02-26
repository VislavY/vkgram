package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.Video
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramColor

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    model: Video,
    shape: Shape = RectangleShape
) {
    Surface(
        modifier = modifier,
        shape = shape
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(
                    data = model.image.last().url,
                    builder = { crossfade(true) }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = VKgramColor.Black.copy(0.5F),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = VKgramColor.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoItem() {
    MainTheme {
        VideoItem(model = Video())
    }
}