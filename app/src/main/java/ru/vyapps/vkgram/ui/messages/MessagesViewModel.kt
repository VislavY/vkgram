package ru.vyapps.vkgram.ui.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.use
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import ru.vyapps.vkgram.data.LongPollServer
import ru.vyapps.vkgram.data.Message
import ru.vyapps.vkgram.data.User
import ru.vyapps.vkgram.data.remote.requests.*
import java.util.concurrent.TimeUnit

class MessagesViewModel(
    private val conversationId: Long
) : ViewModel() {

    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    private val _messages = MutableSharedFlow<List<Message>>()
    val messages = _messages.asSharedFlow()

    private val _newMsg = MutableSharedFlow<Message>()
    val newMsg = _newMsg.asSharedFlow()

    init {
        loadUserById(conversationId)
        loadMessages(conversationId)

        getLongPollServer()
    }

    fun loadMessages(conversationId: Long, offset: Int = 0) {
        VK.execute(GetMessageHistory(conversationId, offset), object: VKApiCallback<List<Message>> {

            override fun success(result: List<Message>) {
                viewModelScope.launch {
                    _messages.emit(result)
                }
            }

            override fun fail(error: Exception) {
                println(error)
            }
        })
    }

    private fun loadUserById(userId: Long) {

        VK.execute(GetUsersByIdRequest(userId), object: VKApiCallback<User?> {

            override fun success(result: User?) {
                result?.let {
                    viewModelScope.launch {
                        _user.emit(it)
                    }

                    println("GGGGGGGGGGGGGGGGGGGGG")
                    println(userId)
                }
            }

            override fun fail(error: Exception) {
                println(error)
            }
        })
    }

    fun sendMessage(text: String) {
        VK.execute(SendMessageRequest(conversationId, text), object: VKApiCallback<Unit> {

            override fun success(result: Unit) {

            }

            override fun fail(error: Exception) {
                println(error)
            }
        })
    }

    private fun getLongPollServer() {
        VK.execute(GetLongPollServerRequest(), object: VKApiCallback<LongPollServer?> {

            override fun success(result: LongPollServer?) {
                result?.let { longPollServer ->
//                    getLongPollHistory(longPollServer.ts)
                    viewModelScope.launch {
                        loadLongPollServer(result)
                    }
                }
            }

            override fun fail(error: Exception) {
                println(error)
            }
        })
    }

    private suspend fun loadLongPollServer(server: LongPollServer) {
        val client = OkHttpClient.Builder()
            .readTimeout(24, TimeUnit.HOURS)
            .build()
        val url = "https://${server.server}?act=a_check&key=${server.key}&ts=${server.ts}&wait=25&mode=2&version=3"
        val request = Request.Builder()
            .url(url)
            .build()
        try {
            val response = client.newCall(request).await()
            response.body.use { body ->
                body?.let {
                    getLongPollHistory(server.ts)

                    val bodyMessage = body.string()
                    val newTs = JSONObject(bodyMessage).getLong("ts")
                    server.ts = newTs
                    loadLongPollServer(server)
                }
            }
        } catch (exception: Exception) {
            println(exception.toString())
        }
    }

    private fun getLongPollHistory(ts: Long) {
        VK.execute(GetLongPollHistoryRequest(ts), object: VKApiCallback<Message?> {

            override fun success(result: Message?) {
                result?.let { message ->
                    if (message.peer_id == conversationId) {
                        viewModelScope.launch {
                            _newMsg.emit(result)
                        }
                    }
                }
            }

            override fun fail(error: Exception) {
                println(error)
            }
        })
    }
}