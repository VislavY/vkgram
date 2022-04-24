package me.vislavy.vkgram.api.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.vislavy.vkgram.api.local.entities.StoredUser

@Dao
interface StoredUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: StoredUser)

    @Query("SELECT * FROM stored_users WHERE id=:id")
    suspend fun fetch(id: Long): StoredUser?
}