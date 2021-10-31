package ru.vyapps.vkgram.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.core.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface CoreBindModule {

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindFriendRepository(impl: FriendRepositoryImpl): FriendRepository

    @Binds
    fun bindConversationRepository(impl: ConversationRepoImpl): ConversationRepo
}