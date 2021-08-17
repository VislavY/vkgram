package ru.vyapps.vkgram.ui.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vyapps.vkgram.data.repositories.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun login(activity: Activity) {
        loginRepository.login(activity)
    }
}
