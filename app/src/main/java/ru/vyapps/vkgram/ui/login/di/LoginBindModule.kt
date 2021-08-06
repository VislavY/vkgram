package ru.vyapps.vkgram.ui.login.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.vyapps.vkgram.data.repositories.LoginRepositoryImpl
import ru.vyapps.vkgram.domain.repositories.LoginRepository
import ru.vyapps.vkgram.ui.login.LoginViewModel
import ru.vyapps.vkgram.utils.ViewModelKey

@Suppress("Unused")
@Module
interface LoginBindModule {

    @Binds
    fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}