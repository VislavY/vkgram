package ru.vyapps.vkgram.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.core.LocalProfile
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.home.R

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
        Spacer(Modifier.width(16.dp))

        Image(
            modifier = Modifier
                .clickable(
                    onClick = {
                        navController.navigate(Destinations.Profile)
                    }
                )
                .clip(CircleShape)
                .size(36.dp),
            painter = rememberImagePainter(
                data = LocalProfile.current.photo,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.photo_placeholder_56)
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null
        )

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