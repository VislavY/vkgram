package me.vislavy.vkgram.core.theme.defaults

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.vislavy.vkgram.core.theme.VKgramTheme

object VKgramButtonDefaults {

    @Composable
    fun buttonColors(
        containerColor: Color = VKgramTheme.palette.primary,
        contentColor: Color = VKgramTheme.palette.onPrimary,
        disabledContainerColor: Color = VKgramTheme.palette.onSurface.copy(0.12F),
        disabledContentColor: Color = VKgramTheme.palette.onSurface.copy(0.38F)

    ): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun secondaryButtonColors(
        containerColor: Color = VKgramTheme.palette.surfaceVariant,
        contentColor: Color = VKgramTheme.palette.primary,
        disabledContainerColor: Color = VKgramTheme.palette.onSurface.copy(0.12F),
        disabledContentColor: Color = VKgramTheme.palette.onSurface.copy(0.38F)

    ): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonColors(
        contentColor: Color = VKgramTheme.palette.primary,
        disabledContentColor: Color = VKgramTheme.palette.onSurface.copy(0.38F)
    ): ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = contentColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun textButtonColors(
        contentColor: Color = VKgramTheme.palette.primary,
        disabledContentColor: Color = VKgramTheme.palette.onSurface.copy(0.38F)
    ): ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = contentColor,
        disabledContentColor =  disabledContentColor
    )
}
