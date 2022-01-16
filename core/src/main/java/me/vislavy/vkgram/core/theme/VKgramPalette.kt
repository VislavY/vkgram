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
    val hintText: Color,
    val defaultMessage: Color,
    val error: Color,
    val background: Color,
    val friendCard: Color,
    val commonFriendCard: Color,
    val subscribesCard: Color,
    val primaryIndicator: Color,
    val secondaryIndicator: Color
) {
    companion object {
        val LightPalette = VKgramPalette(
            primary = VKgramColor.White,
            onPrimary = VKgramColor.BlueGrey900,
            secondary = VKgramColor.LightBlue500,
            onSecondary = VKgramColor.White,
            surface = VKgramColor.White,
            onSurface = VKgramColor.BlueGrey500,
            primaryText = VKgramColor.BlueGrey900,
            secondaryText = VKgramColor.BlueGrey500,
            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey50,
            error = VKgramColor.Red500,
            background = VKgramColor.White,
            friendCard = VKgramColor.Green500,
            commonFriendCard = VKgramColor.Orange500,
            subscribesCard = VKgramColor.Red500,
            primaryIndicator = VKgramColor.LightGreen500,
            secondaryIndicator = VKgramColor.BlueGrey300
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
            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey300,
            error = VKgramColor.Red500,
            background = VKgramColor.DarkGrey,
            friendCard = VKgramColor.Green500,
            commonFriendCard = VKgramColor.Orange500,
            subscribesCard = VKgramColor.Red500,
            primaryIndicator = VKgramColor.LightGreen500,
            secondaryIndicator = VKgramColor.BlueGrey300
        )
    }
}