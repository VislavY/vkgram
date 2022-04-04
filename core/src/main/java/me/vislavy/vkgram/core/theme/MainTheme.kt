package me.vislavy.vkgram.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MainTheme(
    style: VKgramThemeStyle = VKgramThemeStyle.Default,
    darkThemeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val palette = when (darkThemeEnabled) {
        true -> {
            when (style) {
                VKgramThemeStyle.Default -> VKgramPalette.DarkPalette
            }
        }
        false -> {
            when (style) {
                VKgramThemeStyle.Default -> VKgramPalette.LightPalette
            }
        }
    }

    CompositionLocalProvider(
        LocalVKgramPalette provides palette,
        LocalVKgramTypography provides VKgramTypography.Typography,
        content = content
    )
}