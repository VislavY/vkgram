package ru.vyapps.vkgram.vk_api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vyapps.vkgram.vk_api.data.*

interface VkService {

    @GET("users.get?fields=domain,online,last_seen,photo_50,photo_100,photo_200,photo_400_orig&v=5.131")
    suspend fun getUserListByIds(
        @Query("access_token") accessToken: String,
        @Query("user_ids") ids: List<Int>
    ): UserResponse

    @GET("messages.getConversations?extended=1&fields=photo_50,photo_100,photo_200,domain,online&v=5.131")
    suspend fun getConversationList(
        @Query("access_token") accessToken: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): ConversationResponse

    @GET("messages.createChat?v=5.131")
    suspend fun createChat(
        @Query("access_token") accessToken: String,
        @Query("user_ids") userIds: List<Int>,
        @Query("title") title: String
    ): ConversationIdResponse

    @GET("messages.getHistory?v=5.131")
    suspend fun getMessageListByConversationId(
        @Query("access_token") accessToken: String,
        @Query("peer_id") conversationId: Int,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): MessageData

    @GET("messages.getLastActivity?v=5.151")
    suspend fun getLastActivity(
        @Query("access_token") accessToken: String,
        @Query("user_id") userId: Int
    ): LastActivityResponse

    @GET("messages.getChat?v=5.131")
    suspend fun getChatById(
        @Query("access_token") accessToken: String,
        @Query("chat_id") id: Int
    ): ChatResponse

    @GET("messages.send?random_id=0&v=5.131")
    suspend fun sendMessage(
        @Query("access_token") accessToken: String,
        @Query("peer_id") conversationId: Int,
        @Query("message") text: String,
    )

    @GET("messages.getLongPollServer?v=5.131")
    suspend fun getLongPollServer(
        @Query("access_token") accessToken: String
    ): LongPollServerResponse

    @GET("friends.get?fields=domain,photo_50,photo_100,photo_200,photo_400_orig&order=hints&v=5.131")
    suspend fun getFriendList(
        @Query("access_token") accessToken: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): FriendResponse

    @GET("friends.add?v=5.131")
    suspend fun acceptFriend(
        @Query("access_token") accessToken: String,
        @Query("user_id") id: Int
    )

    @GET("friends.delete?v=5.131")
    suspend fun deleteFriend(
        @Query("access_token") accessToken: String,
        @Query("user_id") id: Int
    )

    @GET("groups.getById?v=5.131")
    suspend fun getGroupById(
        @Query("access_token") accessToken: String,
        @Query("group_id") id: Int
    ): GroupResponse

    @GET("account.getProfileInfo?v=5.131")
    suspend fun getProfileInfo(@Query("access_token") accessToken: String): ProfileInfoResponse
}

@ExperimentalSerializationApi
fun VkService(): VkService {
    val json = Json {
        ignoreUnknownKeys = true
    }

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.com/method/")
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    return retrofit.create(VkService::class.java)
}