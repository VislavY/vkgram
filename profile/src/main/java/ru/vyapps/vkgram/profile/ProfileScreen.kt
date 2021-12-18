package ru.vyapps.vkgram.profile

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.vyapps.vkgram.core.views.ErrorContent
import ru.vyapps.vkgram.core.views.LoadingContent
import ru.vyapps.vkgram.profile.models.ProfileEvent
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.profile.views.ProfileContent
import ru.vyapps.vkgram.profile.views.ProfileTopBar

@Composable
fun ProfileScreen(
    userId: Int,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val currentActivity = (LocalContext.current as Activity)
    WindowCompat.setDecorFitsSystemWindows(currentActivity.window, false)
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent)

    val viewState = viewModel.viewState.collectAsState()

    when (val state = viewState.value) {
        is ProfileViewState.Loading -> LoadingContent()
        is ProfileViewState.Error -> ErrorContent(
            onReloadClick = {
                viewModel.onEvent(ProfileEvent.Reload(userId))
            }
        )
        is ProfileViewState.Display -> Scaffold(
            topBar = {
                ProfileTopBar(
                    viewState = state,
                    navController = navController
                )
            }
        ) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            ProfileContent(
                modifier = modifier.padding(bottom = 52.dp),
                viewState = state,
                navController = navController
            )
        }
    }

    LaunchedEffect(viewState) {
        viewModel.onEvent(ProfileEvent.EnterScreen(userId))
    }

    DisposableEffect(viewState) {
        onDispose {
            WindowCompat.setDecorFitsSystemWindows(currentActivity.window, true)
            systemUiController.setStatusBarColor(Color.White)
        }
    }
}