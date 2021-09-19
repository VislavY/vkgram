package ru.vyapps.vkgram.data

sealed class LongPollServerResult

//enum class MessageFlag(val flagId: Int) {
//    UNREAD(1),
//    OUTBOX(2),
//    REPLIED(4),
//    IMPORTANT(8),
//    CHAT(16),
//    FRIENDS(32),
//    SPAM(64),
//    DELETED(128),
//    FIXED(256),
//    MEDIA(512),
//    HIDDEN(65536),
//    DELETE_FOR_ALL(131072),
//    NOT_DELIVERED(262144)
//}

data class NewMessageEvent(
    val conversationId: Long,
    val messageId: Long,
    val message: String,


) : LongPollServerResult()