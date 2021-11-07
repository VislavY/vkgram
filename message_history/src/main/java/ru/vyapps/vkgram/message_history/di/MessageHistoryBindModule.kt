package ru.vyapps.vkgram.message_history.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.message_history.repositories.GroupRepo
import ru.vyapps.vkgram.message_history.repositories.GroupRepoImpl
import ru.vyapps.vkgram.message_history.repositories.MessageRepository
import ru.vyapps.vkgram.message_history.repositories.MessageRepositoryImpl

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface MessageHistoryBindModule {

    @Binds
    fun bindGroupRepo(impl: GroupRepoImpl): GroupRepo

    @Binds
    fun bindMessageRepo(impl: MessageRepositoryImpl): MessageRepository
}