package ru.vyapps.vkgram.login.models

sealed class LoginViewState {
    object Loading: LoginViewState()
    object Display : LoginViewState()
}
