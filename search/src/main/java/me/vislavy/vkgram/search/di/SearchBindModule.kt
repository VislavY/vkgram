package me.vislavy.vkgram.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.vislavy.vkgram.search.repositories.SearchHistoryRepository
import me.vislavy.vkgram.search.repositories.SearchHistoryRepositoryImpl

@Suppress("Unused")
@Module
@InstallIn(SingletonComponent::class)
interface SearchBindModule {

    @Binds
    fun bindSearchHistoryRepository(impl: SearchHistoryRepositoryImpl): SearchHistoryRepository
}