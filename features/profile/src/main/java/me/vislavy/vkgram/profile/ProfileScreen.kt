package me.vislavy.vkgram.profile

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.profile.models.ProfileAction
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.ui.contents.ProfileContent

@Composable
fun ProfileScreen(
    userId: Long = 246847166L,
    navController: NavController = rememberNavController(),
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val action by viewModel.action.collectAsState(null)

    val scaffoldState = rememberScaffoldState()

    ProfileContent(
        state = state,
        scaffoldState = scaffoldState,
        navController = navController,
        uid = userId,
        onEvent = { event ->
            viewModel.onEvent(event)
        }
    )

    LaunchedEffect(action) {
        when (action) {
            is ProfileAction.ShowError -> {
                scaffoldState.snackbarHostState.showSnackbar("@Произошла ошибка при загрузке данных.")
                viewModel.onEvent(ProfileEvent.ClearAction)
            }
            null -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvent.Load(userId))
    }
}