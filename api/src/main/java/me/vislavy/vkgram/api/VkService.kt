package me.vislavy.vkgram.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.vislavy.vkgram.api.data.*
import me.vislavy.vkgram.api.data.ConversationResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.*

interface VkService {

    @GET(
        value = "users.get?v=5.131&fields="
            + "domain"
            + ",online"
            + ",last_seen"
            + ",photo_50"
            + ",photo_100"
            + ",photo_200"
            + ",photo_400_orig"
            + ",bdate"
            + ",personal"
            + ",relatives"
            + ",status"
            + ",city"
            + ",about"
            + ",interests"
            + ",music"
            + ",films"
            + ",books"
            + ",games"
            + ",quotes"
            + ",career"
            + ",home_town"
            + ",relation"
            + ",counters"
            + ",is_friend"
            + ",country"
            + ",friend_status"
    )
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

    @GET("messages.getConversationsById?extended=1&fields=photo_50,photo_100,photo_200,domain,online&v=5.131")
    suspend fun getConversationListById(
        @Query("access_token") accessToken: String,
        @Query("peer_ids") ids: String
    ): ConversationByIdData

    @GET("messages.searchConversations?extended=1&fields=photo_50,photo_100,photo_200,domain,online&v=5.131")
    suspend fun findConversation(
        @Query("access_token") accessToken: String,
        @Query("q") name: String,
        @Query("count") count: Int
    ): ConversationByIdData

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

    @GET("messages.deleteConversation?v=5.131")
    suspend fun deleteConversation(
        @Query("access_token") accessToken: String,
        @Query("peer_id") id: Int
    )

    @GET("messages.getHistoryAttachments?media_type=photo&v=5.131")
    suspend fun getDialogAttachments(
        @Query("access_token") accessToken: String,
        @Query("peer_id") dialogId: Int,
        @Query("count") count: Int,
        @Query("start_from") offset: Int
    ): AttachmentResponse

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

    @GET("friends.search?fields=domain,photo_50,photo_100,photo_200,photo_400_orig&order=hints&v=5.131")
    suspend fun findFriendsByName(
        @Query("access_token") accessToken: String,
        @Query("q") name: String,
        @Query("count") count: Int
    ): FriendResponse

    @GET("groups.getById?v=5.131")
    suspend fun getGroupById(
        @Query("access_token") accessToken: String,
        @Query("group_id") id: Int
    ): GroupResponse

    @GET("account.getProfileInfo?v=5.131")
    suspend fun getProfileInfo(@Query("access_token") accessToken: String): ProfileInfoResponse

    @GET("photos.getMessagesUploadServer?v=5.131")
    suspend fun getMessagesUploadServer(@Query("access_token") accessToken: String): UploadServerResponse

    @GET("photos.saveMessagesPhoto?v=5.131")
    suspend fun saveMessagesPhoto(
        @Query("access_token") accessToken: String,
        @Query("photo") photo: String,
        @Query("server") server: Int,
        @Query("hash") hash: String
    ): PhotoResponse

    @Multipart
    @POST
    suspend fun uploadFile(
        @Url uploadServerUrl: String,
        @Part file: MultipartBody.Part
    ): UploadFileResult

    @GET("account.setSilenceMode?v=5.131")
    suspend fun setSilenceMode(
        @Query("access_token") accessToken: String,
        @Query("peer_id") dialogId: Int,
        @Query("sound") sound: Byte,
        @Query("time") time: Int
    ): Byte

    @GET("friends.areFriends?v=5.131")
    suspend fun areFriends(
        @Query("access_token") accessToken: String,
        @Query("user_ids") userIds: List<Int>,
    ): FriendStatusResponse
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