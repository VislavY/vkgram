package me.vislavy.vkgram.profile.ui.attachments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vk.sdk.api.video.dto.VideoVideo
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.utils.formatDuration

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    model: VideoVideo
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier,
        color = VKgramTheme.palette.surface
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(model.image?.last()?.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(contentAlignment = Alignment.BottomStart) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                    color = VKgramTheme.palette.onSurface.copy(0.38F),
                    shape = CircleShape
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onPrimary
                    )

                    Text(
                        text = formatDuration(model.duration!!),
                        color = VKgramTheme.palette.onPrimary,
                        style = VKgramTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.width(6.dp))
                }
            }
        }
    }
}