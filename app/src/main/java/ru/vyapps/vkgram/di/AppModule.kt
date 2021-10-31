package ru.vyapps.vkgram.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.core.VkAccessToken
import ru.vyapps.vkgram.vk_api.VkService

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideVkAccessToken(@ApplicationContext context: Context): VkAccessToken {
        return VkAccessToken(context)
    }

    @ExperimentalSerializationApi
    @Provides
    fun provideVkService(): VkService {
        return VkService()
    }
}