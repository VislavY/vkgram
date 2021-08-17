package ru.vyapps.vkgram.data.repositories

import android.app.Activity
import ru.vyapps.vkgram.data.remote.LoginService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun login(activity: Activity) {
        loginService.login(activity)
    }
}