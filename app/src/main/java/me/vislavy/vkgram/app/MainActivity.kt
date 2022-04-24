package me.vislavy.vkgram.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStore
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStoreProvider
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.profile.ProfileScreen

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalSerializationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resources = resources
        val lang = getString(R.string.lang)
        val appId = resources.getInteger(R.integer.com_vk_sdk_AppId)
        val vkApiConfig = VKApiConfig(this, langProvider = { lang }, appId =  appId)
        VK.setConfig(vkApiConfig)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, true)
            }

            LocalSettingsDataStoreProvider {
                val settingsDataStore = LocalSettingsDataStore.current
                val darkThemeEnabledState =
                    settingsDataStore.getDarkThemeEnabled().collectAsState(false)
                MainTheme(
                    darkThemeEnabled = (isSystemInDarkTheme() || darkThemeEnabledState.value)
                ) {
                    if (VK.isLoggedIn()) ProfileScreen() else NavGraph(Destinations.Login)

//                        val startDestination = if (VK.isLoggedIn())
//                            Destinations.Profile else Destinations.Login
//                        NavGraph(startDestination)
                }
            }
        }
    }
}