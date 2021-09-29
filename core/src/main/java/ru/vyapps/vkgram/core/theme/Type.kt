package ru.vyapps.vkgram.core.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.vyapps.vkgram.core.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_light, FontWeight.Thin),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black)
)

val Typography = Typography(
    h4 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),

    h5 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),

    h6 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),

    subtitle1 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),

    subtitle2 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    body1 = TextStyle(
        color = BlueGrey300,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    body2 = TextStyle(
      color = BlueGrey300,
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
        color = BlueGrey300,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )
)

