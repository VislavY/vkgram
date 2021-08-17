package ru.vyapps.vkgram.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.data.remote.LoginService
import ru.vyapps.vkgram.data.repositories.LoginRepository
import ru.vyapps.vkgram.data.repositories.LoginRepositoryImpl
import ru.vyapps.vkgram.ui.MainActivity

@Module
@InstallIn(SingletonComponent::class)
interface AppBindModule {

    @Suppress("Unused")
    @Binds
    fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository
}