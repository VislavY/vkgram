package ru.vyapps.vkgram.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.vyapps.vkgram.core.views.ErrorContent
import ru.vyapps.vkgram.core.views.LoadingContent
import ru.vyapps.vkgram.profile.models.ProfileEvent
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.profile.views.ProfileContent
import ru.vyapps.vkgram.profile.views.ProfileTopBar

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            ProfileTopBar(navController = navController)
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        when (val state = viewState.value) {
            is ProfileViewState.Loading -> LoadingContent(modifier)
            is ProfileViewState.Error -> ErrorContent(
                modifier = modifier,
                onReloadClick = {
                    viewModel.onEvent(ProfileEvent.Reload)
                }
            )
            is ProfileViewState.Display -> ProfileContent(
                modifier = modifier,
                viewState = state
            )
            else -> throw NotImplementedError("Unexpected profile state")
        }
    }

    LaunchedEffect(viewState) {
        viewModel.onEvent(ProfileEvent.EnterScreen)
    }
}