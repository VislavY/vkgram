package me.vislavy.vkgram.core.theme

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
    val warningText: Color,
    val background: Color,
    val friendCard: Color,
    val commonFriendCard: Color,
    val subscribesCard: Color,
    val indicator: Color
) {
    companion object {
        val LightPalette = VKgramPalette(
            primary = VKgramColor.White,
            onPrimary = VKgramColor.BlueGrey900,
            secondary = VKgramColor.LightBlue500,
            onSecondary = VKgramColor.White,
            surface = VKgramColor.White,
            onSurface = VKgramColor.BlueGrey300,
            primaryText = VKgramColor.BlueGrey900,
            secondaryText = VKgramColor.BlueGrey300,
            warningText = VKgramColor.Red500,
            background = VKgramColor.White,
            friendCard = VKgramColor.Green500,
            commonFriendCard = VKgramColor.Orange500,
            subscribesCard = VKgramColor.Red500,
            indicator = VKgramColor.LightGreen500
        )

        val DarkPalette = VKgramPalette(
            primary = VKgramColor.DarkGrey,
            onPrimary = VKgramColor.BlueGrey900,
            secondary = VKgramColor.LightBlue200,
            onSecondary = VKgramColor.White,
            surface = VKgramColor.DarkGrey,
            onSurface = VKgramColor.Grey700,
            primaryText = VKgramColor.White,
            secondaryText = VKgramColor.Grey300,
            warningText = VKgramColor.Red500,
            background = VKgramColor.DarkGrey,
            friendCard = VKgramColor.Green500,
            commonFriendCard = VKgramColor.Orange500,
            subscribesCard = VKgramColor.Red500,
            indicator = VKgramColor.LightGreen500
        )
    }
}