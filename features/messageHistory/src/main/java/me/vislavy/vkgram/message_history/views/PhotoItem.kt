package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.core.theme.MainTheme

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    model: Photo,
    shape: Shape = RectangleShape
) {
    Surface(
        modifier = modifier,
        shape = shape
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(
                data = model.sizes.last().url,
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun PreviewPhotoItem() {
    MainTheme {
        PhotoItem(model = Photo())
    }
}