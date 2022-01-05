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
import me.vislavy.vkgram.api.local.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideVkAccessToken(@ApplicationContext context: Context) = VkAccessToken(context)

    @ExperimentalSerializationApi
    @Provides
    fun provideVkService() = VkService()

    @Singleton
    @Provides
    fun provideLogPollServer(
        vkAccessToken: VkAccessToken,
        vkService: VkService
    ) = LongPollServerManager(vkAccessToken, vkService)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase(context)

    @Provides
    fun provideLastConversationDatabase(appDatabase: AppDatabase) =
        appDatabase.getLastConversationDao()
}