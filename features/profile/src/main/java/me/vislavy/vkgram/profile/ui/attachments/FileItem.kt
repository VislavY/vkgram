package me.vislavy.vkgram.profile.ui.attachments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vk.sdk.api.docs.dto.DocsDoc
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.utils.formatDate
import me.vislavy.vkgram.profile.utils.formatSize
import java.util.*

@Composable
fun FileItem(
    modifier: Modifier = Modifier,
    model: DocsDoc
) {
    val context = LocalContext.current
    val lastSeen = Date(model.date * 1000L)

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.surface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            if (model.preview?.photo != null) {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(context)
                        .data(model.preview?.photo?.sizes?.last()?.src)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
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
                        imageVector = Icons.Rounded.Description,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onPrimary
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = model.title,
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${formatSize(model.size)}, ${formatDate(lastSeen).lowercase()}",
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}