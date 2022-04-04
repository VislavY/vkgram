package me.vislavy.vkgram.profile.views.attachment.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.api.data.PhotoSize
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfilePhotoItem(
    modifier: Modifier = Modifier,
    model: Photo
) {
    Surface(
        modifier = modifier,
        color = VKgramTheme.palette.surfaceVariant
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Outlined.Photo,
                contentDescription = null,
                tint = VKgramTheme.palette.onSurfaceVariant
            )
        }

        Image(
            painter = rememberImagePainter(model.sizes.last().url),
            contentDescription = null
        )
    }
}

@Preview("Profile photo item")
@Composable
fun PreviewProfilePhotoItem() {
    MainTheme {
        ProfilePhotoItem(
            modifier = Modifier.size(100.dp),
            model = Photo(sizes = listOf(PhotoSize()))
        )
    }
}

@Preview("Profile photo item dark theme")
@Composable
fun PreviewProfilePhotoItem_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfilePhotoItem(
            modifier = Modifier.size(100.dp),
            model = Photo(sizes = listOf(PhotoSize()))
        )
    }
}