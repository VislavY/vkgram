package me.vislavy.vkgram.core.datastore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalSettingsDataStore = compositionLocalOf<SettingsDataStore> {
    error("No SettingsDataStore provided")
}

@Composable
fun LocalSettingsDataStoreProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    CompositionLocalProvider(
        LocalSettingsDataStore provides SettingsDataStore(context),
        content = content
    )
}