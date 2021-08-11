package ru.vyapps.vkgram.ui.login.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap
import ru.vyapps.vkgram.data.repositories.LoginRepositoryImpl
import ru.vyapps.vkgram.data.repositories.LoginRepository
import ru.vyapps.vkgram.ui.login.LoginViewModel
import ru.vyapps.vkgram.utils.ViewModelKey


@Suppress("Unused")
@Module
interface LoginBindModule {

    @Reusable
    @Binds
    fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Reusable
    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}