package me.vislavy.vkgram.profile

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
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileViewState
import me.vislavy.vkgram.profile.views.ProfileContent
import me.vislavy.vkgram.profile.views.ProfileTopBar

@Composable
fun ProfileScreen(
    userId: Int,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val state = viewState.value) {
        is ProfileViewState.Loading -> LoadingContent()
        is ProfileViewState.Error -> ErrorContent(
            onReloadClick = {
                viewModel.onIntent(ProfileEvent.Reload(userId))
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
        viewModel.onIntent(ProfileEvent.EnterScreen(userId))
    }
}