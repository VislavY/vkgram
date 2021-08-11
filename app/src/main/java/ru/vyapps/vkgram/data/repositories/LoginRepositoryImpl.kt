package ru.vyapps.vkgram.data.repositories

import dagger.Reusable
import ru.vyapps.vkgram.data.api.LoginService
import javax.inject.Inject

@Reusable
class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun login() {
        loginService.login()
    }

    override fun isLoggedIn(): Boolean {
        return loginService.isLoggedIn()
    }
}