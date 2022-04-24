package me.vislavy.vkgram.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import dagger.hilt.android.AndroidEntryPoint
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStore
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStoreProvider
import me.vislavy.vkgram.core.theme.MainTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val resources = resources
        val lang = getString(R.string.lang)
        val appId = resources.getInteger(R.integer.com_vk_sdk_AppId)
        val vkApiConfig = VKApiConfig(this, langProvider = { lang }, appId =  appId)
        VK.setConfig(vkApiConfig)

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
                    val startDestination = if (VK.isLoggedIn())
                        Destinations.Home else Destinations.Login
                    NavGraph(startDestination)
                }
            }
        }
    }
}