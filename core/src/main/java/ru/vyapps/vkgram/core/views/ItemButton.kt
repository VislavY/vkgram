package ru.vyapps.vkgram.core.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun ItemButton(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .background(VKgramTheme.palette.background)
            .fillMaxWidth(),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RectangleShape,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = VKgramTheme.palette.background),
        content = content
    )
}