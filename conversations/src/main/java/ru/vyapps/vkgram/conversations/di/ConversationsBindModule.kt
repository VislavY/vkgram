package ru.vyapps.vkgram.conversations.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.conversations.repositories.*

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface ConversationsBindModule {

    @Binds
    fun bindConversationRepo(impl: ConversationRepoImpl): ConversationRepo

    @Binds
    fun bindLongPollServerRepo(impl: LongPollServerRepoImpl): LongPollServerRepo

    @Binds
    fun bindUserRepo(impl: UserRepoImpl): UserRepo
}