package ru.vyapps.vkgram.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class LoginViewModel : ViewModel() {

    fun login(activity: Activity) {
        val scopes = listOf(
            VKScope.MESSAGES,
            VKScope.FRIENDS
        )
        VK.login(activity, scopes)
    }
}