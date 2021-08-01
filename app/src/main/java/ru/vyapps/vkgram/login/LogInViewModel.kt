package ru.vyapps.vkgram.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogInViewModel : ViewModel() {

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
}