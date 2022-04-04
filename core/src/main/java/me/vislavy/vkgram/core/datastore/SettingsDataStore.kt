package me.vislavy.vkgram.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import me.vislavy.vkgram.core.extensions.dataStore

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    private val inputIndicatorEnabledKey = booleanPreferencesKey("InputIndicatorEnabled")
    private val readingMessagesEnabledKey = booleanPreferencesKey("ReadingMessagesEnabled")
    private val darkThemeEnabledKey = booleanPreferencesKey("DarkThemeEnabled")

    fun getInputIndicatorEnabled() = dataStore.data.map { settings ->
        settings[inputIndicatorEnabledKey] ?: true
    }

    suspend fun setInputIndicatorEnabled(value: Boolean) {
        dataStore.edit { settings ->
            settings[inputIndicatorEnabledKey] = value
        }
    }

    fun getReadingMessagesEnabled() = dataStore.data.map { settings ->
        settings[readingMessagesEnabledKey] ?: true
    }

    suspend fun setReadingMessagesEnabled(value: Boolean) {
        dataStore.edit { settings ->
            settings[readingMessagesEnabledKey] = value
        }
    }

    fun getDarkThemeEnabled() = dataStore.data.map { settings ->
        settings[darkThemeEnabledKey] ?: false
    }

    suspend fun setDarkThemeEnabled(value: Boolean) {
        dataStore.edit { settings ->
            settings[darkThemeEnabledKey] = value
        }
    }
}