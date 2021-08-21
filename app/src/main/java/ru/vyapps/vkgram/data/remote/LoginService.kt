package ru.vyapps.vkgram.data.remote

import android.app.Activity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class LoginService @Inject constructor() {

    private val scopes = listOf(VKScope.MESSAGES)

    fun login(activity: Activity) {
        VK.login(activity, scopes)
    }
}