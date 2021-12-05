package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.vk_api.data.User

@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    viewState: ProfileViewState.Display,
    navController: NavController = rememberNavController()
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

//        Image(
//            modifier = Modifier
//                .height(250.dp)
//                .fillMaxWidth(),
//            painter = rememberImagePainter(
//                data = viewState.user.photoOrig
//            ),
//            contentDescription = null,
//            contentScale = ContentScale.FillWidth,
//        )
    }
}

@Preview
@Composable
fun ProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample"
                )
            )
        )
    }
}

@Preview
@Composable
fun DarkProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample"
                )
            )
        )
    }
}