package me.vislavy.vkgram.core.theme.defaults

import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.vislavy.vkgram.core.theme.VKgramTheme

object VKgramTopAppBarDefaults {

    @Composable
    fun smallTopAppBarColors(
        containerColor: Color = VKgramTheme.palette.surface,
        scrolledContainerColor: Color = VKgramTheme.palette.surfaceAtPrimary,
        navigationIconContentColor: Color = VKgramTheme.palette.onSurface,
        titleContentColor: Color = VKgramTheme.palette.onSurface,
        actionIconContentColor: Color = VKgramTheme.palette.onSurfaceVariant
    ): TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = containerColor,
        scrolledContainerColor = scrolledContainerColor,
        navigationIconContentColor = navigationIconContentColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconContentColor
    )
}