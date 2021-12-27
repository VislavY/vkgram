package ru.vyapps.vkgram.login

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import kotlinx.coroutines.delay
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.login.models.LoginEvent
import ru.vyapps.vkgram.login.models.LoginViewState
import ru.vyapps.vkgram.login.views.LoginContent
import ru.vyapps.vkgram.login.views.LoginLoadingContent

@Composable
fun LoginScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavController,
    viewModel: LoginViewModel
) {
    val systemUiController = rememberSystemUiController()
    val viewState = viewModel.viewState.collectAsState()

    SideEffect {
        systemUiController.setStatusBarColor(Color(0xFF01a9f4))
        systemUiController.setNavigationBarColor(Color(0xFF81d4fa))
    }

    val currentActivity = (LocalContext.current as Activity)
    when (viewState.value) {
        is LoginViewState.Loading -> LoginLoadingContent()
        is LoginViewState.Display -> LoginContent(onLoginClick = {
            viewModel.onEvent(LoginEvent.OnLoginButtonClick(currentActivity))
        })
    }

    LaunchedEffect(viewState) {
        while (true) {
            if (VK.isLoggedIn()) {
                navController.navigate(Destinations.Home)
            }

            delay(1000)
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            systemUiController.setSystemBarsColor(Color.White)
        }
    }
}