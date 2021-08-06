package ru.vyapps.vkgram.ui.login

import androidx.lifecycle.ViewModel
import ru.vyapps.vkgram.domain.iterators.LoginUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun login() {
        loginUseCase.login()
    }
}