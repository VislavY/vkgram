package ru.vyapps.vkgram.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.home.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface ConversationsBindModule {

    @Binds
    fun bindLongPollServerRepo(impl: LongPollServerRepoImpl): LongPollServerRepo
}