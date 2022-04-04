package me.vislavy.vkgram.profile.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.R

@Composable
fun ProfileTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    SmallTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.profile_back_arrow_icon)
                )
            }
        },
        title = { },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.profile_more_icon)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = VKgramTheme.palette.surface,
            navigationIconContentColor = VKgramTheme.palette.onSurface,
            actionIconContentColor = VKgramTheme.palette.onSurfaceVariant
        )
    )
}

@Preview("Profile top app bar")
@Composable
fun PreviewProfileTopAppBar() {
    MainTheme {
        ProfileTopAppBar()
    }
}

@Preview("Profile top app bar dark theme")
@Composable
fun PreviewProfileTopAppBar_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileTopAppBar()
    }
}