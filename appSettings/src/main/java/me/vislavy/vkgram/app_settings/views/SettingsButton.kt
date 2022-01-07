package me.vislavy.vkgram.app_settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextButton(
        modifier = modifier.height(58.dp),
        onClick = onClick,
        enabled = enabled,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        shape = RectangleShape,
        colors = ButtonDefaults.textButtonColors(backgroundColor = VKgramTheme.palette.background)
    ) {
        leadingIcon?.invoke()

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1F)) {
            Text(
                text = title,
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.body1
            )

            subtitle?.let {
                Text(
                    text = it,
                    color = VKgramTheme.palette.secondaryText,
                    style = VKgramTheme.typography.body2
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        trailingIcon?.invoke()
    }
}

@Preview
@Composable
fun PreviewSettingsButton() {
    MainTheme {
        SettingsButton(
            onClick = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            },
            title = "Themes",
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = VKgramTheme.palette.secondary,
                            shape = CircleShape
                        )
                )
            },
        )
    }
}