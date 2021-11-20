package ru.vyapps.vkgram.profile.views

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = VKgramTheme.palette.background,
        elevation = 0.dp
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun ProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(navController = rememberNavController())
    }
}

@Preview
@Composable
fun DarkProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(navController = rememberNavController())
    }
}