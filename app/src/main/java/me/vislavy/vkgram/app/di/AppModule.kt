package me.vislavy.vkgram.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.api.LongPollServerManager
import me.vislavy.vkgram.api.VkAccessToken
import me.vislavy.vkgram.api.VkService
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