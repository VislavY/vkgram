package me.vislavy.vkgram.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.core.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface CoreBindModule {

    @Binds
    fun bindConversationRepository(impl: ConversationRepositoryImpl): ConversationRepository
}