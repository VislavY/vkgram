package me.vislavy.vkgram.profile.ui.attachments

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vk.sdk.api.photos.dto.PhotosPhoto
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    model: PhotosPhoto
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier,
        color = VKgramTheme.palette.surface
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(model.sizes?.last()?.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}