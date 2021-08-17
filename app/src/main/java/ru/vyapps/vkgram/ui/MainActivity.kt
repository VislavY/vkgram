package ru.vyapps.vkgram.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.android.AndroidEntryPoint
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ui.theme.VKgramTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VKgramTheme {
                val startDestination = if (VK.isLoggedIn())
                    Destinations.CONVERSATIONS_SCREEN else Destinations.LOGIN_SCREEN
                NavGraph(startDestination)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
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