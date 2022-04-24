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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vk.sdk.api.audio.dto.AudioAudio
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.utils.formatDuration

@Composable
fun AudioItem(
    modifier: Modifier = Modifier,
    model: AudioAudio
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.surface
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = VKgramTheme.palette.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onPrimary
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1F)) {
                Text(
                    text = model.title,
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = model.artist,
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = formatDuration(model.duration),
                color = VKgramTheme.palette.onSurfaceVariant,
                style = VKgramTheme.typography.bodySmall
            )
        }
    }
}