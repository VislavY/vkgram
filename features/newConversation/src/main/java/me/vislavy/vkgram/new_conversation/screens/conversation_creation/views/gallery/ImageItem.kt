package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import java.io.File

@ExperimentalCoilApi
@Composable
fun ImageItem(
    imageFile: File,
    onClick: (File) -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable {
                onClick(imageFile)
            }
            .size(128.dp)
    ) {
        Image(
            painter = rememberImagePainter(imageFile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun ImageItem_Preview() {
    ImageItem(
        imageFile = File("/"),
        onClick = { }
    )
}