package ru.vyapps.vkgram.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vyapps.vkgram.domain.iterators.LoginWithVkUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginWithVkUseCase: LoginWithVkUseCase
) : ViewModel() {

    private var mutableLogInButtonEnabled = MutableLiveData(false)
    val logInButtonEnabled: LiveData<Boolean> = mutableLogInButtonEnabled

    var login: String? = null
    var password: String? = null

    /**
     *  Enable the log in button if login and password are not null or blank.
     */
    fun checkData() {
        mutableLogInButtonEnabled.value =
            !login.isNullOrBlank() && !password.isNullOrBlank()
    }

    fun loginWithVk() {
        loginWithVkUseCase.loginWithVk()
    }
}