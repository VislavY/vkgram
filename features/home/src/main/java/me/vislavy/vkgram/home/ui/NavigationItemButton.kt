package me.vislavy.vkgram.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun NavigationItemButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (RowScope.() -> Unit),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RectangleShape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = VKgramTheme.palette.background,
            disabledBackgroundColor = VKgramTheme.palette.background
        ),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
    ) {
        Box(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                this.icon()

                Spacer(Modifier.width(16.dp))

                this.content()
            }
        }
    }
}

@Preview
@Composable
fun NavigationItemButton_Preview() {
    MainTheme {
        NavigationItemButton(
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onPrimary
                )
            }
        ) {
            Text(
                text = "Your profile",
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.button
            )
        }
    }
}

@Preview
@Composable
fun DarkNavigationItemButton_Preview() {
    MainTheme(darkThemeEnabled = true) {
        NavigationItemButton(
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onPrimary
                )
            }
        ) {
            Text(
                text = "Your profile",
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.button
            )
        }
    }
}