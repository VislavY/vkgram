package me.vislavy.vkgram.login.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme

@Composable
fun LoginLoadingContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF01a9f4),
                        Color(0xFF81d4fa)
                    )
                )
            )
            .fillMaxSize(),
        color = Color.Transparent
    ) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun LoginLoadingContent_Preview() {
    MainTheme {
        LoginLoadingContent()
    }
}

@Preview
@Composable
fun DarkLoginLoadingContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        LoginLoadingContent()
    }
}