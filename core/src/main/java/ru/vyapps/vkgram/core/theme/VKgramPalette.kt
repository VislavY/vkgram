package ru.vyapps.vkgram.core.theme

import androidx.compose.ui.graphics.Color

data class VKgramPalette(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val surface: Color,
    val onSurface: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val background: Color,
) {
    companion object {
        val LightPalette = VKgramPalette(
            primary = VKgramColor.White,
            onPrimary = VKgramColor.BlueGrey900,
            secondary = VKgramColor.LightBlue500,
            onSecondary = VKgramColor.White,
            surface = VKgramColor.BlueGrey50,
            onSurface = VKgramColor.BlueGrey300,
            primaryText = VKgramColor.BlueGrey900,
            secondaryText = VKgramColor.BlueGrey300,
            background = VKgramColor.White
        )

        val DarkPalette = VKgramPalette(
            primary = VKgramColor.DarkGrey,
            onPrimary = VKgramColor.BlueGrey900,
            secondary = VKgramColor.LightBlue200,
            onSecondary = VKgramColor.White,
            surface = VKgramColor.Grey900,
            onSurface = VKgramColor.Grey700,
            primaryText = VKgramColor.White,
            secondaryText = VKgramColor.Grey300,
            background = VKgramColor.DarkGrey
        )
    }
}