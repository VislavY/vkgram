package ru.vyapps.vkgram.home.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = VKgramTheme.palette.primary,
        elevation = 0.dp
    ) {
        IconButton(
            onClick = {
                navController.navigate(Destinations.Profile)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.Search,
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
        HomeTopBar(navController = rememberNavController())
    }
}

@Preview
@Composable
fun DarkHomeTopBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HomeTopBar(navController = rememberNavController())
    }
}