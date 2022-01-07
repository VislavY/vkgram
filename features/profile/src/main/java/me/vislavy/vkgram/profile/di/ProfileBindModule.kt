package me.vislavy.vkgram.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.profile.repositories.AccountRepository
import me.vislavy.vkgram.profile.repositories.AccountRepositoryImpl

@Suppress("Unused")
@Module
@InstallIn(SingletonComponent::class)
interface ProfileBindModule {

    @Binds
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}