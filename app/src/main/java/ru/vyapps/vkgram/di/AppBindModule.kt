package ru.vyapps.vkgram.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.data.repositories.ConversationsRepository
import ru.vyapps.vkgram.data.repositories.ConversationsRepositoryImpl
import ru.vyapps.vkgram.data.repositories.LoginRepository
import ru.vyapps.vkgram.data.repositories.LoginRepositoryImpl

@Suppress("Unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppBindModule {

    @Binds
    fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    fun bindConversationsRepository(
        impl: ConversationsRepositoryImpl
    ): ConversationsRepository
}