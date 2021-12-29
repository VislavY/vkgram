package me.vislavy.vkgram.login.models

import android.app.Activity

sealed class LoginEvent {
    data class OnLoginButtonClick(val activity: Activity) : LoginEvent()
}