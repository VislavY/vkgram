package ru.vyapps.vkgram.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.data.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface AppBindModule {

    @Binds
    fun bindMessageRepo(
        impl: MessageRepoImpl
    ): MessageRepo

    @Binds
    fun bindUserRepo(
        impl: UserRepoImpl
    ): UserRepo
}