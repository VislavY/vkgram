package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramColor
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileCountCard(
    modifier: Modifier = Modifier,
    count: Int = 0,
    subtitle: String,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        elevation = 7.dp
    ) {
        Box(
            modifier = modifier
                .background(
                    brush = Brush.linearGradient(
                        start = Offset(0F, 0F),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                        colors = listOf(
                            VKgramColor.LightBlue200,
                            VKgramColor.LightBlue500
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (count < 1000) "$count" else "$count K",
                    color = Color.White,
                    style = VKgramTheme.typography.topBarTitle
                )

                Text(
                    text = subtitle,
                    color = Color.White,
                    style = VKgramTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileCountCard() {
    MainTheme {
        ProfileCountCard(subtitle = "Friends")
    }
}

@Preview
@Composable
fun PreviewDarkProfileCountCard() {
    MainTheme(darkThemeEnabled = true) {
        ProfileCountCard(subtitle = "Friends")
    }
}