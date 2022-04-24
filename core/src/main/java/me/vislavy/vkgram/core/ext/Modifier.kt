package me.vislavy.vkgram.core.ext

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import me.vislavy.vkgram.core.theme.VKgramTheme

fun Modifier.vkgramPlaceholder() = composed {
    Modifier.placeholder(
        visible = true,
        color = VKgramTheme.palette.onSurfaceVariant.copy(0.38F),
        shape = CircleShape,
        highlight = PlaceholderHighlight.fade(VKgramTheme.palette.surfaceVariant),
    )
}
