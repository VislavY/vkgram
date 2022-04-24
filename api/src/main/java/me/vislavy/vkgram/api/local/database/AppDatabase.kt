package me.vislavy.vkgram.api.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.vislavy.vkgram.api.local.entities.LastConversation
import me.vislavy.vkgram.api.local.entities.StoredUser

@Database(
    entities = [LastConversation::class, StoredUser::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLastConversationDao(): LastConversationDao

    abstract fun getStoredUserDao(): StoredUserDao
}

fun AppDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()