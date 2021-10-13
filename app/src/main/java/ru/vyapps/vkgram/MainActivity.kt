package ru.vyapps.vkgram

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import ru.vyapps.vkgram.conversations.ConversationsViewModel
import ru.vyapps.vkgram.message_history.MessageHistoryViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    @ExperimentalCoilApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val startDestination = if (VK.isLoggedIn())
                Destinations.CONVERSATION_LIST_SCREEN else Destinations.LOGIN_SCREEN
            NavGraph(startDestination)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {

            override fun onLogin(token: VKAccessToken) {
                val preferences = getPreferences(Context.MODE_PRIVATE)
                with (preferences.edit()) {
                    putString(getString(R.string.token_pref_key), token.accessToken)
                    apply()
                }

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

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {

        fun provideConversationsViewModelFactory(): ConversationsViewModel.Factory

        fun provideMessageHistoryViewModelFactory(): MessageHistoryViewModel.Factory
    }
}