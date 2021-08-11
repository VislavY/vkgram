package ru.vyapps.vkgram.ui.login

import androidx.lifecycle.ViewModel
import ru.vyapps.vkgram.data.repositories.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun login() {
        loginRepository.login()
    }

    fun userIsLoggedIn(): Boolean {
        return loginRepository.isLoggedIn()
    }
}