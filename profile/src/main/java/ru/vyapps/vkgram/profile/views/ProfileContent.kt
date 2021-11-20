package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.vk_api.data.ProfileInfo

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewState: ProfileViewState.Display
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {

    }
}

@Composable
@Preview
fun ProfileContent_Preview() {
    MainTheme {
        ProfileContent(viewState = ProfileViewState.Display(ProfileInfo()))
    }
}

@Composable
@Preview
fun DarkProfileContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ProfileContent(viewState = ProfileViewState.Display(ProfileInfo()))
    }
}