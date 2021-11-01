package ru.vyapps.vkgram.home.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun HomeTopBar() {
    TopAppBar(
        backgroundColor = VKgramTheme.palette.primary,
        elevation = 0.dp
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBar_Preview() {
    MainTheme {
        HomeTopBar()
    }
}

@Preview
@Composable
fun DarkHomeTopBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HomeTopBar()
    }
}