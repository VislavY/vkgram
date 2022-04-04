package me.vislavy.vkgram.profile.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileLoadingContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.fade(),
                            color = VKgramTheme.palette.surfaceVariant,
                            shape = CircleShape
                        )
                )

                Spacer(Modifier.weight(1F))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.fade(),
                            color = VKgramTheme.palette.surfaceVariant,
                            shape = CircleShape
                        )
                )

                Spacer(Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(40.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.fade(),
                            color = VKgramTheme.palette.surfaceVariant,
                            shape = CircleShape
                        )
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(28.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                        color = VKgramTheme.palette.surfaceVariant,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(16.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                        color = VKgramTheme.palette.surfaceVariant,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(152.dp)
                    .height(20.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                        color = VKgramTheme.palette.surfaceVariant,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.height(16.dp))

            repeat(2) { index ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.fade(),
                                color = VKgramTheme.palette.surfaceVariant,
                                shape = CircleShape
                            )
                    )

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(125.dp)
                            .height(20.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.fade(),
                                color = VKgramTheme.palette.surfaceVariant,
                                shape = CircleShape
                            )
                    )
                }

                if (index == 0) Spacer(Modifier.height(4.dp))
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.fade(),
                                color = VKgramTheme.palette.surfaceVariant,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Preview("Profile loading content")
@Composable
fun PreviewProfileLoadingContent() {
    MainTheme {
        ProfileLoadingContent()
    }
}

@Preview("Profile loading content dark theme")
@Composable
fun PreviewProfileLoadingContent_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileLoadingContent()
    }
}