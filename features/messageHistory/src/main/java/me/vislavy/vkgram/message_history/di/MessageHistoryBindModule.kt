package me.vislavy.vkgram.message_history.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.message_history.repositories.GroupRepo
import me.vislavy.vkgram.message_history.repositories.GroupRepoImpl
import me.vislavy.vkgram.message_history.repositories.MessageRepository
import me.vislavy.vkgram.message_history.repositories.MessageRepositoryImpl

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface MessageHistoryBindModule {

    @Binds
    fun bindGroupRepo(impl: GroupRepoImpl): GroupRepo

    @Binds
    fun bindMessageRepo(impl: MessageRepositoryImpl): MessageRepository
}