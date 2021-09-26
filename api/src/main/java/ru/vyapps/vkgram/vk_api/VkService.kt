package ru.vyapps.vkgram.vk_api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vyapps.vkgram.vk_api.data.ConversationData
import ru.vyapps.vkgram.vk_api.data.MessageData
import ru.vyapps.vkgram.vk_api.data.UserData

interface VkService {

    @GET("method/messages.getConversations?extended=1&fields=photo_50,photo_100,photo_200&v=5.131")
    suspend fun getConversations(
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("access_token") token: String,
    ): ConversationData

    @GET("method/messages.getHistory?v=5.131")
    suspend fun getMessagesByConversationId(
        @Query("peer_id") conversationId: Long,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("access_token") token: String
    ): MessageData

    @GET("method/users.get?fields=photo_50,photo_100,photo_200&v=5.131")
    suspend fun getUserById(
        @Query("user_ids") userId: Long,
        @Query("access_token") token: String
    ): UserData

    @GET("method/messages.send?random_id=0&v=5.131")
    suspend fun sendMessage(
        @Query("peer_id") conversationId: Long,
        @Query("message") text: String,
        @Query("access_token") token: String,
    )
}

@ExperimentalSerializationApi
fun VkService(): VkService {
    val json = Json {
        ignoreUnknownKeys = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.com/")
        .addConverterFactory(
            json.asConverterFactory(MediaType.parse("application/json")!!)
        )
        .build()

    return retrofit.create(VkService::class.java)
}