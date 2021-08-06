package ru.vyapps.vkgram.data.repositories

import ru.vyapps.vkgram.data.api.LoginService
import ru.vyapps.vkgram.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun login() {
        loginService.login()
    }
}