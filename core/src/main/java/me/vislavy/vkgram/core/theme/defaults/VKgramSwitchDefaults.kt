package me.vislavy.vkgram.core.theme.defaults

import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.vislavy.vkgram.core.theme.VKgramTheme

object VKgramSwitchDefaults {

    @Composable
    fun switchColors(
        checkedThumbColor: Color = VKgramTheme.palette.primary,
        disabledCheckedThumbColor: Color = VKgramTheme.palette.primary.copy(0.38F),
        checkedTrackColor: Color = VKgramTheme.palette.primaryContainer,
        disabledCheckedTrackColor: Color = VKgramTheme.palette.primary.copy(0.12F),
        uncheckedThumbColor: Color = VKgramTheme.palette.onSurfaceVariant,
        disabledUncheckedThumbColor: Color = VKgramTheme.palette.onSurfaceVariant.copy(0.38F),
        uncheckedTrackColor: Color = VKgramTheme.palette.surfaceVariant,
        disabledUncheckedTrackColor: Color = VKgramTheme.palette.onSurfaceVariant.copy(0.12F),
    ) = SwitchDefaults.colors(
        checkedThumbColor = checkedThumbColor,
        disabledCheckedThumbColor = disabledCheckedThumbColor,
        checkedTrackColor = checkedTrackColor,
        disabledCheckedTrackColor = disabledCheckedTrackColor,
        checkedTrackAlpha = 1F,
        uncheckedThumbColor = uncheckedThumbColor,
        disabledUncheckedThumbColor = disabledUncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        disabledUncheckedTrackColor = disabledUncheckedTrackColor,
        uncheckedTrackAlpha = 1F
    )
}