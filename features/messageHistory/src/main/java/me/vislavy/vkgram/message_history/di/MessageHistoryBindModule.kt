package me.vislavy.vkgram.message_history.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.message_history.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface MessageHistoryBindModule {

    @Binds
    fun bindGroupRepo(impl: GroupRepoImpl): GroupRepo

    @Binds
    fun bindMessageRepo(impl: MessageRepositoryImpl): MessageRepository

    @Binds
    fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository
}