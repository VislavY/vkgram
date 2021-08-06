package ru.vyapps.vkgram.domain.iterators

import ru.vyapps.vkgram.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginWithVkUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){

    fun loginWithVk() {
        loginRepository.loginWithVk()
    }
}