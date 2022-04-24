package me.vislavy.vkgram.app_settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.vislavy.vkgram.app_settings.models.AppSettingsIntent
import me.vislavy.vkgram.app_settings.models.AppSettingsViewState
import me.vislavy.vkgram.app_settings.views.AppSettingsContent
import me.vislavy.vkgram.app_settings.views.AppSettingsTopBar
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent

@Composable
fun AppSettingsScreen(
    viewModel: AppSettingsViewModel,
    navController: NavController
) {
    val viewState = viewModel.viewState.collectAsState()

//    when (val state = viewState.value) {
//        is AppSettingsViewState.Loading -> LoadingContent()
//        is AppSettingsViewState.Error -> ErrorContent(onReloadClick = {
//            viewModel.onEvent(AppSettingsIntent.ReloadScreen)
//        })
//        is AppSettingsViewState.Display -> Scaffold(topBar = {
//            AppSettingsTopBar(navController = navController)
//        }) { paddingValues ->
//            val modifier = Modifier.padding(paddingValues)
//            AppSettingsContent(modifier = modifier)
//        }
//    }
//
//    LaunchedEffect(viewState) {
//        viewModel.onEvent(AppSettingsIntent.EnterScreen)
//    }
}