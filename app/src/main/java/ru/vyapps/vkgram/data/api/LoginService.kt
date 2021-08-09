package ru.vyapps.vkgram.data.api

import android.app.Activity
import com.vk.api.sdk.VK
import javax.inject.Inject

class LoginService @Inject constructor(
    private val activity: Activity
) {

    fun login() {
        VK.login(activity)
    }
}