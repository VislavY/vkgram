package me.vislavy.vkgram.core.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class VKgramTypography(
    // Display
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,

    // Headline
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,

    // Title
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,

    // Label
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,

    // Body
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,

    val topBarTitle: TextStyle,
    val h6: TextStyle,
    val searchText: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle,
    val caption: TextStyle
) {
    companion object {
        val Typography = VKgramTypography(
            // Display
            displayLarge = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 64.sp,
                fontSize = 57.sp,
                fontWeight = FontWeight.Normal
            ),
            displayMedium = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 52.sp,
                fontSize = 45.sp,
                fontWeight = FontWeight.Normal
            ),
            displaySmall = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 44.sp,
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal
            ),

            // Headline
            headlineLarge = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 40.sp,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal
            ),
            headlineMedium = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 36.sp,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            ),
            headlineSmall = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 32.sp,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            ),

            // Title
            titleLarge = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 28.sp,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            ),
            titleMedium = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            titleSmall = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 20.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),

            // Label
            labelLarge = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 20.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            labelMedium = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 16.sp,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            ),
            labelSmall = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 6.sp,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            ),

            // Body
            bodyLarge = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            bodyMedium = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 20.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            bodySmall = TextStyle(
                fontFamily = RobotoFontFamily,
                lineHeight = 16.sp,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),

            topBarTitle = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            ),

            h6 = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),

            searchText = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),

            subtitle1 = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            ),

            subtitle2 = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),

            body1 = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),

            body2 = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),

            button = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),

            caption = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
        )
    }
}
