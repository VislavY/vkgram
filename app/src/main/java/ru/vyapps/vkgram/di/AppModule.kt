package ru.vyapps.vkgram.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.vk_api.LongPollServerManager
import ru.vyapps.vkgram.vk_api.VkAccessToken
import ru.vyapps.vkgram.vk_api.VkService
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideLogPollServer(
        vkAccessToken: VkAccessToken,
        vkService: VkService
    ): LongPollServerManager {
        return LongPollServerManager(vkAccessToken, vkService)
    }
}