package me.vislavy.vkgram.core.theme

import androidx.compose.ui.graphics.Color

data class VKgramPalette(
    // Primary
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,

    // Error
    val error: Color,
    val onError: Color,
    val errorContainer: Color,

    // Surface
    val background: Color,
    val surface: Color,
    val surfaceAtPrimary: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,


    val hintText: Color,
    val defaultMessage: Color,
    val placeholder: Color
) {
    companion object {
        val LightPalette = VKgramPalette(
            // Primary
            primary = VKgramColor.PrimaryLight,
            onPrimary = VKgramColor.OnPrimaryLight,
            primaryContainer = VKgramColor.PrimaryContainerLight,

            // Error
            error = VKgramColor.ErrorLight,
            onError = VKgramColor.OnErrorLight,
            errorContainer = VKgramColor.ErrorContainerLight,

            // Surface
            background = VKgramColor.BackgroundLight,
            surface = VKgramColor.SurfaceLight,
            surfaceAtPrimary = VKgramColor.SurfaceAtPrimaryLight,
            onSurface = VKgramColor.OnSurfaceLight,
            surfaceVariant = VKgramColor.SurfaceVariantLight,
            onSurfaceVariant = VKgramColor.OnSurfaceVariantLight,

            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey50,
            placeholder = VKgramColor.Grey200
        )

        val DarkPalette = VKgramPalette(
            // Primary
            primary = VKgramColor.PrimaryDark,
            onPrimary = VKgramColor.OnPrimaryDark,
            primaryContainer = VKgramColor.PrimaryContainerDark,

            // Error
            error = VKgramColor.ErrorDark,
            onError = VKgramColor.OnErrorDark,
            errorContainer = VKgramColor.ErrorContainerDark,

            // Surface
            background = VKgramColor.BackgroundDark,
            surface = VKgramColor.SurfaceDark,
            surfaceAtPrimary = VKgramColor.SurfaceAtPrimaryDark,
            onSurface = VKgramColor.OnSurfaceDark,
            surfaceVariant = VKgramColor.SurfaceVariantDark,
            onSurfaceVariant = VKgramColor.OnSurfaceVariantDark,

            hintText = VKgramColor.BlueGrey300,
            defaultMessage = VKgramColor.BlueGrey300,
            placeholder = VKgramColor.Grey200
        )
    }
}