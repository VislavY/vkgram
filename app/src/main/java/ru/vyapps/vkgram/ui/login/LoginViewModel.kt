package ru.vyapps.vkgram.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    fun login(activity: Activity) {
        val scopes = listOf(
            VKScope.MESSAGES
        )
        VK.login(activity, scopes)
    }
}
