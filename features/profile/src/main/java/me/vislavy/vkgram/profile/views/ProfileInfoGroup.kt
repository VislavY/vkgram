package me.vislavy.vkgram.profile.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileInfoGroup(
    modifier: Modifier = Modifier,
    header: String,
    isExpanded: Boolean = false,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Surface(
        modifier = modifier.animateContentSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            var isExpandedState by remember { mutableStateOf(isExpanded) }
            val angle by animateFloatAsState(
                targetValue = if (isExpandedState) 0F else 180F,
                animationSpec = tween (easing = FastOutSlowInEasing)
            )
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            isExpandedState = !isExpandedState
                        }
                    )
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Text(
                    text = header,
                    color = VKgramTheme.palette.secondary,
                    style = VKgramTheme.typography.subtitle2
                )

                Spacer(Modifier.width(16.dp))

                Icon(
                    modifier = Modifier.rotate(angle),
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }

            if (isExpandedState) Column(content = content)

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileInfoGroup() {
    MainTheme {
        ProfileInfoGroup(header = "Personal information") { }
    }
}

@Preview
@Composable
fun PreviewDarkProfileInfoGroup() {
    MainTheme(darkThemeEnabled = true) {
        ProfileInfoGroup(header = "Personal information") { }
    }
}