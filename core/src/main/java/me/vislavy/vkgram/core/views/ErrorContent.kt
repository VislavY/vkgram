package me.vislavy.vkgram.core.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    tint = VKgramTheme.palette.onPrimary
                )

                Text(
                    text = "Произошла ошибка при загрузке данных",
                    color = VKgramTheme.palette.onSurface,
                    textAlign = TextAlign.Center,
                    style = VKgramTheme.typography.h6
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = onReloadClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = VKgramTheme.palette.primary)
                ) {
                    Text(
                        text = "Перезагрузить",
                        color = Color.White,
                        style = VKgramTheme.typography.button
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorContent_Preview() {
    MainTheme {
        ErrorContent(onReloadClick = { })
    }
}

@Preview
@Composable
fun DarkErrorContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ErrorContent(onReloadClick = { })
    }
}