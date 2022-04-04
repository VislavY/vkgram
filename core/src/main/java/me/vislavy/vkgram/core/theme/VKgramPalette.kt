package me.vislavy.vkgram.core.theme

import androidx.compose.ui.graphics.Color

data class VKgramPalette(
    // Primary
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    // Secondary
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    // Error
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    // Surface
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,


    val primaryText: Color,
    val secondaryText: Color,
    val hintText: Color,
    val defaultMessage: Color,
    val primaryIndicator: Color,
    val secondaryIndicator: Color,
    val placeholder: Color
) {
    companion object {
        val LightPalette = VKgramPalette(
            // Primary
            primary = VKgramColor.Primary40,
            onPrimary = VKgramColor.Primary100,
            primaryContainer = VKgramColor.Primary90,
            onPrimaryContainer = VKgramColor.Primary10,

            // Secondary
            secondary = VKgramColor.Secondary40,
            onSecondary = VKgramColor.Secondary100,
            secondaryContainer = VKgramColor.Secondary90,
            onSecondaryContainer = VKgramColor.Secondary10,

            // Error
            error = VKgramColor.Error40,
            onError = VKgramColor.Error100,
            errorContainer = VKgramColor.Error90,
            onErrorContainer = VKgramColor.Error10,

            // Surface
            background = VKgramColor.Neutral99,
            onBackground = VKgramColor.Neutral10,
            surface = VKgramColor.Neutral99,
            onSurface = VKgramColor.Neutral10,
            surfaceVariant = VKgramColor.NeutralVariant90,
            onSurfaceVariant = VKgramColor.NeutralVariant30,
            outline = VKgramColor.NeutralVariant50,

            primaryText = VKgramColor.Black,
            secondaryText = VKgramColor.BlueGrey500,
            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey50,
            primaryIndicator = VKgramColor.Black.copy(0.85F),
            secondaryIndicator = VKgramColor.BlueGrey300,
            placeholder = VKgramColor.Grey200
        )

        val DarkPalette = VKgramPalette(
            // Primary
            primary = VKgramColor.Primary80,
            onPrimary = VKgramColor.Primary20,
            primaryContainer = VKgramColor.Primary30,
            onPrimaryContainer = VKgramColor.Primary90,

            // Secondary
            secondary = VKgramColor.Secondary80,
            onSecondary = VKgramColor.Secondary20,
            secondaryContainer = VKgramColor.Secondary30,
            onSecondaryContainer = VKgramColor.Secondary90,

            // Error
            error = VKgramColor.Error80,
            onError = VKgramColor.Error20,
            errorContainer = VKgramColor.Error30,
            onErrorContainer = VKgramColor.Error90,

            // Surface
            background = VKgramColor.Neutral10,
            onBackground = VKgramColor.Neutral90,
            surface = VKgramColor.Neutral10,
            onSurface = VKgramColor.Neutral80,
            surfaceVariant = VKgramColor.NeutralVariant30,
            onSurfaceVariant = VKgramColor.NeutralVariant80,
            outline = VKgramColor.NeutralVariant60,

            primaryText = VKgramColor.White,
            secondaryText = VKgramColor.Grey300,
            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey300,
            primaryIndicator = VKgramColor.LightGreen500,
            secondaryIndicator = VKgramColor.BlueGrey300,
            placeholder = VKgramColor.Grey200
        )
    }
}