package ru.vyapps.vkgram.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vyapps.vkgram.core.repositories.ConversationRepo
import ru.vyapps.vkgram.core.repositories.ConversationRepoImpl
import ru.vyapps.vkgram.core.repositories.UserRepo
import ru.vyapps.vkgram.core.repositories.UserRepoImpl

@Suppress("UNUSED")
@Module
@InstallIn(SingletonComponent::class)
interface CoreBindModule {

    @Binds
    fun bindUserRepo(impl: UserRepoImpl): UserRepo

    @Binds
    fun bindConversationRepo(impl: ConversationRepoImpl): ConversationRepo
}