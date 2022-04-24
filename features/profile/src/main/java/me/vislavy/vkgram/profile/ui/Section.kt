package me.vislavy.vkgram.profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun Section(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    leadingIcon: (@Composable () -> Unit)? = null,
    body: @Composable () -> Unit = { },
    trailingIcon: @Composable () -> Unit = { },
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = VKgramTheme.palette.onSurfaceVariant.copy(.8F)),
                onClick = onClick
            ),
        color = VKgramTheme.palette.surface,
        contentColor = VKgramTheme.palette.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                it()

                Spacer(Modifier.width(16.dp))
            }

            body()

            Spacer(Modifier.weight(1F))

            trailingIcon()
        }
    }
}