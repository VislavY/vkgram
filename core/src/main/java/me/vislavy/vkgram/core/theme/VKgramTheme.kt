package me.vislavy.vkgram.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

enum class VKgramThemeStyle {
    Default
}

object VKgramTheme {
    val palette: VKgramPalette
        @Composable
        get() = LocalVKgramPalette.current
    val typography: VKgramTypography
        @Composable
        get() = LocalVKgramTypography.current
}

val LocalVKgramPalette = staticCompositionLocalOf<VKgramPalette> {
    error("No colors provided")
}

val LocalVKgramTypography = staticCompositionLocalOf<VKgramTypography> {
    error("No typography provided")
}