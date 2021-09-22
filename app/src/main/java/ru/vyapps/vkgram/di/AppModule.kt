package ru.vyapps.vkgram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.vk_api.VkService

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ExperimentalSerializationApi
    @Provides
    fun provideVkService(): VkService {
        return VkService()
    }
}