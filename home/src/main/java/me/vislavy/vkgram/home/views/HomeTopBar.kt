package me.vislavy.vkgram.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.home.R

@Composable
fun HomeTopBar(
    userModel: User,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val navDialogInOpenedState = remember { mutableStateOf(false) }
    NavigationDialog(
        isOpenedState = navDialogInOpenedState,
        profileModel = userModel,
        navController = navController
    )

    TopAppBar(
        modifier = modifier,
        backgroundColor = VKgramTheme.palette.primary,
        elevation = 0.dp
    ) {
        Spacer(Modifier.width(16.dp))

        Card(
            modifier = Modifier
                .clickable {
                    navDialogInOpenedState.value = true
                }
                .height(32.dp),
            shape = RoundedCornerShape(32.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(12.dp))

                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(24.dp),
                    painter = rememberImagePainter(
                        data = userModel.photo,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.photo_placeholder_56)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = userModel.firstName,
                    color = VKgramTheme.palette.primaryText,
                    style = VKgramTheme.typography.body2
                )

                Spacer(Modifier.width(12.dp))
            }
        }

        Spacer(Modifier.weight(1f))

        IconButton(onClick = {
            navController.navigate(Destinations.Search)
        }) {
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
        HomeTopBar(
            userModel = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            navController = rememberNavController()
        )
    }
}

@Preview
@Composable
fun DarkHomeTopBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HomeTopBar(
            userModel = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            navController = rememberNavController()
        )
    }
}