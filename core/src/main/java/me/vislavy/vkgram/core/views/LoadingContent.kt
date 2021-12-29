package me.vislavy.vkgram.core.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
                color = VKgramTheme.palette.onSurface
            )
        }
    }
}

@Preview
@Composable
fun LoadingContent_Preview() {
    MainTheme() {
        LoadingContent()
    }
}

@Preview
@Composable
fun DarkLoadingContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        LoadingContent()
    }
}