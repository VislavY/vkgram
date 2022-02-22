package me.vislavy.vkgram.core.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun VKgramDivider(
    modifier: Modifier = Modifier,
    color: Color = VKgramTheme.palette.onSurface.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
    horizontalIndent: Dp = 16.dp
) {
    Divider(
        modifier = modifier.padding(horizontal = horizontalIndent),
        color = color,
        thickness = thickness
    )
}

private const val DividerAlpha = 0.24F