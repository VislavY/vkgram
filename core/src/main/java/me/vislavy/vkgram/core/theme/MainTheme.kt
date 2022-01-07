package me.vislavy.vkgram.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MainTheme(
    style: VKgramThemeStyle = VKgramThemeStyle.Default,
    fontSize: VKgramTypography.FontSize = VKgramTypography.FontSize.Normal,
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

    val typography = when (fontSize) {
        VKgramTypography.FontSize.Small -> VKgramTypography.SmallTypography
        VKgramTypography.FontSize.Normal -> VKgramTypography.NormalTypography
        VKgramTypography.FontSize.Medium -> VKgramTypography.MediumTypography
        VKgramTypography.FontSize.Big -> VKgramTypography.BigTypography
    }


    CompositionLocalProvider(
        LocalVKgramPalette provides palette,
        LocalVKgramTypography provides typography,
        content = content
    )
}