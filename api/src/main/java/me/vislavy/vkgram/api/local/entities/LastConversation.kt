package me.vislavy.vkgram.api.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LastConversations")
data class LastConversation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val conversationId: Int
)