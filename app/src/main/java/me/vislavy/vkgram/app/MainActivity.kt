package me.vislavy.vkgram.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStore
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStoreProvider
import me.vislavy.vkgram.core.theme.MainTheme

@ExperimentalMaterial3Api
@ExperimentalAnimatedInsets
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

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, true)
            }

            LocalSettingsDataStoreProvider {
                val settingsDataStore = LocalSettingsDataStore.current
                val darkThemeEnabledState = settingsDataStore.getDarkThemeEnabled().collectAsState(false)
                MainTheme(
                    darkThemeEnabled = (isSystemInDarkTheme() || darkThemeEnabledState.value)
                ) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        val startDestination = if (VK.isLoggedIn())
                            Destinations.Home else Destinations.Login
                        NavGraph(startDestination)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {

            override fun onLogin(token: VKAccessToken) {
                val sharedPreferences =
                    getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE)
                sharedPreferences!!.edit()
                    .putString(getString(R.string.access_token_pref_key), token.accessToken).apply()

                showSuccessfulLoginToast()
            }

            override fun onLoginFailed(errorCode: Int) {
                showFailedLoginToast()
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showSuccessfulLoginToast() {
        Toast.makeText(
            this,
            R.string.login_successful,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showFailedLoginToast() {
        Toast.makeText(
            this,
            R.string.login_failed,
            Toast.LENGTH_SHORT
        ).show()
    }
}