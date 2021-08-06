package ru.vyapps.vkgram.data.api

import android.app.Activity
import com.vk.api.sdk.VK
import ru.vyapps.vkgram.MainActivity
import javax.inject.Inject

class LoginService @Inject constructor(
    private val activity: Activity
) {

    fun loginWithVk() {

        VK.login(activity)
    }
}