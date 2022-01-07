package me.vislavy.vkgram.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.home.repositories.LongPollServerRepo
import me.vislavy.vkgram.home.repositories.LongPollServerRepoImpl

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface ConversationsBindModule {

    @Binds
    fun bindLongPollServerRepo(impl: LongPollServerRepoImpl): LongPollServerRepo
}