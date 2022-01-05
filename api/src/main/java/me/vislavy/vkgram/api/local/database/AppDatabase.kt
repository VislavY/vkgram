package me.vislavy.vkgram.api.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.vislavy.vkgram.api.local.entities.LastConversation

@Database(
    entities = [LastConversation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLastConversationDao(): LastConversationDao
}

fun AppDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()