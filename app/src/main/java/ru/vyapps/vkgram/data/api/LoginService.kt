package ru.vyapps.vkgram.data.api

import android.app.Activity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.Reusable
import javax.inject.Inject

@Reusable
class LoginService @Inject constructor(
    private val activity: Activity
) {

    private val scopes = arrayListOf(
        VKScope.MESSAGES
    )

    fun login() {
        VK.login(activity, scopes)
    }

    fun isLoggedIn(): Boolean {
        return VK.isLoggedIn()
    }
}