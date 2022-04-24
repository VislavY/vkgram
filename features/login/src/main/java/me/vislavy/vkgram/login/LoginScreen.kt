package me.vislavy.vkgram.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.login.ui.LoginContent

@Composable
fun LoginScreen(navController: NavController) {
    val systemUiController = rememberSystemUiController()

    val launch = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) { result ->
        when (result) {
            is VKAuthenticationResult.Failed -> Unit
            is VKAuthenticationResult.Success -> Unit
        }
    }
    LoginContent(onLoginClick = {
        launch.launch(setOf(VKScope.FRIENDS, VKScope.MESSAGES))
    })

    LaunchedEffect(VK.isLoggedIn()) {
        if (VK.isLoggedIn()) {
            navController.navigate(Destinations.Home)
        }
    }

    SideEffect {
        systemUiController.statusBarDarkContentEnabled = false
    }

    DisposableEffect(Unit) {
        onDispose {
            systemUiController.statusBarDarkContentEnabled = true
        }
    }
}