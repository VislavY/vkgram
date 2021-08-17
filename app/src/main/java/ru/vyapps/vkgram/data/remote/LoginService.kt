package ru.vyapps.vkgram.data.remote

import android.app.Activity
import com.vk.api.sdk.VK
import javax.inject.Inject

class LoginService @Inject constructor(){

    fun login(activity: Activity) {
        VK.login(activity)
    }
}