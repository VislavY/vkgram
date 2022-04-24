package me.vislavy.vkgram.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.delay
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.login.models.LoginEvent
import me.vislavy.vkgram.login.models.LoginViewState
import me.vislavy.vkgram.login.views.LoginContent
import me.vislavy.vkgram.login.views.LoginLoadingContent

@Composable
fun LoginScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavController
) {
    val launch = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) { result ->
        when (result) {
            is VKAuthenticationResult.Failed -> Unit
            is VKAuthenticationResult.Success -> Unit
        }
    }
    LoginContent(onLoginClick = {
        launch.launch(setOf(VKScope.FRIENDS, VKScope.MESSAGES))
    })
}