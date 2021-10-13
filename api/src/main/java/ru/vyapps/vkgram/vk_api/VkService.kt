package ru.vyapps.vkgram.vk_api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
    import retrofit2.http.GET
import retrofit2.http.Query
import ru.vyapps.vkgram.vk_api.data.ConversationData
import ru.vyapps.vkgram.vk_api.data.LongPollServerResponse
import ru.vyapps.vkgram.vk_api.data.MessageData
import ru.vyapps.vkgram.vk_api.data.UserData

interface VkService {

    @GET("messages.getConversations?extended=1&fields=photo_50,photo_100,photo_200&v=5.131")
    suspend fun getConversations(
        @Query("access_token") accessToken: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): ConversationData

    @GET("messages.getHistory?v=5.131")
    suspend fun getMessagesByConversationId(
        @Query("access_token") accessToken: String,
        @Query("peer_id") conversationId: Long,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): MessageData

    @GET("users.get?fields=photo_50,photo_100,photo_200&v=5.131")
    suspend fun getUserById(
        @Query("access_token") accessToken: String,
        @Query("user_ids") ids: IntArray
    ): UserData

    @GET("messages.send?random_id=0&v=5.131")
    suspend fun sendMessage(
        @Query("access_token") accessToken: String,
        @Query("peer_id") conversationId: Long,
        @Query("message") text: String,
    )

    @GET("messages.getLongPollServer?v=5.131")
    suspend fun getLongPollServer(
        @Query("access_token") accessToken: String
    ): LongPollServerResponse
}

@ExperimentalSerializationApi
fun VkService(): VkService {
    val json = Json {
        ignoreUnknownKeys = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.com/method/")
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaTypeOrNull()!!)
        )
        .build()

    return retrofit.create(VkService::class.java)
}