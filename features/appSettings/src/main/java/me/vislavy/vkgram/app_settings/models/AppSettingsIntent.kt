package me.vislavy.vkgram.app_settings.models

sealed class AppSettingsIntent {
    object EnterScreen : AppSettingsIntent()
    object ReloadScreen : AppSettingsIntent()
}
