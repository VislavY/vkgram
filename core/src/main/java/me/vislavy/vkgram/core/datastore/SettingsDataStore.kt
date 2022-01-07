package me.vislavy.vkgram.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import me.vislavy.vkgram.core.extensions.dataStore
import me.vislavy.vkgram.core.theme.VKgramTypography

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    private val fontSizeKey = stringPreferencesKey("FontSize")
    private val inputIndicatorEnabledKey = booleanPreferencesKey("InputIndicatorEnabled")
    private val readingMessagesEnabledKey = booleanPreferencesKey("ReadingMessagesEnabled")
    private val darkThemeEnabledKey = booleanPreferencesKey("DarkThemeEnabled")

    fun getFontSize() = dataStore.data.map { settings ->
        val fontSizeName = settings[fontSizeKey]
        if (fontSizeName == null) {
            VKgramTypography.FontSize.Normal
        } else {
            VKgramTypography.FontSize.valueOf(fontSizeName)
        }
    }

    suspend fun setFontSize(value: VKgramTypography.FontSize) {
        dataStore.edit { settings ->
            settings[fontSizeKey] = value.name
        }
    }

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