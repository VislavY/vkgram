package ru.vyapps.vkgram.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface VkService {

    @GET("method/messages.getConversations?extended=1&fields=photo_50,photo_100,photo_200&v=5.131")
    fun getConversations(
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("access_token") token: String,
    ): Call<ConversationData>

    @GET("method/messages.getHistory?v=5.131")
    fun getMessagesByConversationId(
        @Query("peer_id") conversationId: Long,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("access_token") token: String
    ): Call<MessageHistoryData>

    @GET("method/users.get?fields=photo_50,photo_100,photo_200&v=5.131")
    fun getUserById(
        @Query("user_ids") userId: Long,
        @Query("access_token") token: String
    ): Call<UserData>

    @GET("method/messages.send?random_id=0&v=5.131")
    fun sendMessage(
        @Query("peer_id") conversationId: Long,
        @Query("message") text: String,
        @Query("access_token") token: String,
    ): Call<Unit>
}



