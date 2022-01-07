package me.vislavy.vkgram.app_settings.models

import me.vislavy.vkgram.core.theme.VKgramTypography

sealed class AppSettingsViewState {
    object Loading : AppSettingsViewState()
    object Error : AppSettingsViewState()
    object Display : AppSettingsViewState()
}