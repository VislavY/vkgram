package me.vislavy.vkgram.search.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun PlaceholderFoundItem(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.background,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .placeholder(
                        visible = true,
                        color = VKgramTheme.palette.surface,
                        shape = CircleShape,
                        highlight = PlaceholderHighlight.shimmer(Color.White)
                    )
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(14.dp)
                            .placeholder(
                                visible = true,
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape,
                                highlight = PlaceholderHighlight.shimmer(Color.White)
                            )
                    )

                    Spacer(Modifier.width(14.dp))

                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(14.dp)
                            .placeholder(
                                visible = true,
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape,
                                highlight = PlaceholderHighlight.shimmer(Color.White)
                            )
                    )
                }

                Spacer(Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(14.dp)
                        .placeholder(
                            visible = true,
                            color = VKgramTheme.palette.surface,
                            shape = CircleShape,
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPlaceholderFoundItem() {
    MainTheme {
        PlaceholderFoundItem()
    }
}