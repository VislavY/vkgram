package ru.vyapps.vkgram.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.vyapps.vkgram.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_light, FontWeight.Thin),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black)
)

val Typography = Typography(
    h5 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    subtitle1 = TextStyle(
        color = BlueGrey300,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    subtitle2 = TextStyle(
        color = BlueGrey700,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
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
        fontSize = 12.sp
    )
)

