package me.vislavy.vkgram.api.local.database

import androidx.room.*
import me.vislavy.vkgram.api.local.entities.LastConversation

@Dao
interface LastConversationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(lastConversations: Array<out LastConversation>)

    @Query("DELETE FROM LastConversations WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM LastConversations ORDER BY id DESC LIMIT :count")
    suspend fun getLast(count: Int): List<LastConversation>

    @Query("DELETE FROM LastConversations")
    suspend fun clear()
}