package me.vislavy.vkgram.core.theme.defaults

import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import me.vislavy.vkgram.core.theme.VKgramTheme

object VKgramMenuDefaults {

    @Composable
    fun itemColors(): MenuItemColors = MenuDefaults.itemColors(
        textColor = VKgramTheme.palette.onSurface,
        leadingIconColor = VKgramTheme.palette.onSurfaceVariant,
        trailingIconColor = VKgramTheme.palette.onSurfaceVariant,
        disabledTextColor = VKgramTheme.palette.onSurface.copy(0.38F),
        disabledLeadingIconColor = VKgramTheme.palette.onSurface.copy(0.12F),
        disabledTrailingIconColor = VKgramTheme.palette.onSurface.copy(0.12F),
    )
}