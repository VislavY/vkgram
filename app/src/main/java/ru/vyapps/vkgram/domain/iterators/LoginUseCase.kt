package ru.vyapps.vkgram.domain.iterators

import ru.vyapps.vkgram.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){

    fun login() {
        loginRepository.login()
    }
}