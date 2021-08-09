package ru.vyapps.vkgram

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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