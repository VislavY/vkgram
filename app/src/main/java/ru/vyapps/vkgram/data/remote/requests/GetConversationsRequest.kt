package ru.vyapps.vkgram.data.remote.requests

import com.vk.api.sdk.requests.VKRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.vyapps.vkgram.data.Conversation
import ru.vyapps.vkgram.data.Group
import ru.vyapps.vkgram.data.User
import java.lang.Exception
import kotlin.collections.ArrayList

class GetConversationsRequest(count: Int) : VKRequest<List<Conversation>>("messages.getConversations") {

    init {
        addParam("count", count)
        addParam("extended", 1)
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<Conversation> {
        val responseJObj = r.getJSONObject("response")
        val json = Json { ignoreUnknownKeys = true }

        val itemsJArray = responseJObj.getJSONArray("items")
        val conversations = ArrayList<Conversation>()
        for (i in 0 until itemsJArray.length()) {
            val itemJObj = itemsJArray.getJSONObject(i)
            val conversationJObj = JSONObject()
            conversationJObj.put(
                "id",
                itemJObj
                    .getJSONObject("conversation")
                    .getJSONObject("peer")
                    .getLong("id")
            )
            val type = itemJObj
                .getJSONObject("conversation")
                .getJSONObject("peer")
                .getString("type")
            conversationJObj.put("type", type)

            if (type == "chat") {
                try {
                    conversationJObj.put(
                        "avatar",
                        itemJObj
                            .getJSONObject("conversation")
                            .getJSONObject("chat_settings")
                            .getJSONObject("photo")
                            .getString("photo_200")
                    )
                } catch (e: Exception) {}

                conversationJObj.put(
                    "title",
                    itemJObj
                        .getJSONObject("conversation")
                        .getJSONObject("chat_settings")
                        .getString("title")
                )
            }

            conversationJObj.put(
                "last_message",
                itemJObj
                    .getJSONObject("last_message")
                    .getString("text")
            )
            conversationJObj.put(
                "last_message_date",
                itemJObj
                    .getJSONObject("last_message")
                    .getLong("date")
            )

            val conversation = json.decodeFromString<Conversation>(conversationJObj.toString())
            conversations.add(conversation)
        }

        val profilesJArray = responseJObj.getJSONArray("profiles")
        val users = ArrayList<User>()
        for (i in 0 until profilesJArray.length()) {
            val profileJObj = profilesJArray.getJSONObject(i)
            val user = json.decodeFromString<User>(profileJObj.toString())
            users.add(user)
        }

        val groupsJArray = responseJObj.getJSONArray("groups")
        val groups = ArrayList<Group>()
        for (i in 0 until groupsJArray.length()) {
            val groupJObj = groupsJArray.getJSONObject(i)
            val group = json.decodeFromString<Group>(groupJObj.toString())
            groups.add(group)
        }

        // TODO: Needs optimization
        for (conversation in conversations) {
            if (conversation.type == "user") {
                for (user in users) {
                    if (conversation.id == user.id) {
                        conversation.title = "${user.first_name} ${user.last_name}"
                        conversation.avatar = user.photo_200
                    }
                }
            } else if (conversation.type == "group") {
                for (group in groups) {
                    if (conversation.id == -group.id) {
                        conversation.title = group.name
                        conversation.avatar = group.photo_200
                    }
                }
            }
        }

        return conversations
    }
}