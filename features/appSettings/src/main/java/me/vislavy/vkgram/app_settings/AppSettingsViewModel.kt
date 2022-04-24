package me.vislavy.vkgram.app_settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.vislavy.vkgram.app_settings.models.AppSettingsIntent
import me.vislavy.vkgram.app_settings.models.AppSettingsViewState
import me.vislavy.vkgram.core.base.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor() : ViewModel(){

    private val _viewState = MutableStateFlow<AppSettingsViewState>(AppSettingsViewState.Loading)
    val viewState = _viewState.asStateFlow()
//
//    override fun onEvent(event: AppSettingsIntent) {
//        when (val currentState = _viewState.value) {
//            is AppSettingsViewState.Loading -> reduce(event, currentState)
//            is AppSettingsViewState.Error -> reduce(event, currentState)
//            is AppSettingsViewState.Display -> reduce(event, currentState)
//        }
//    }
//
//    private fun reduce(intent: AppSettingsIntent, currentState: AppSettingsViewState.Loading) {
//        when (intent) {
//            is AppSettingsIntent.EnterScreen -> enterScreen()
//            else -> throw NotImplementedError("Invalid $intent for state $currentState")
//        }
//    }
//
//    private fun reduce(intent: AppSettingsIntent, currentState: AppSettingsViewState.Error) {
//        when (intent) {
//            is AppSettingsIntent.ReloadScreen -> enterScreen()
//            else -> throw NotImplementedError("Invalid $intent for state $currentState")
//        }
//    }
//
//    private fun reduce(intent: AppSettingsIntent, currentState: AppSettingsViewState.Display) {
//        when (intent) {
//            is AppSettingsIntent.EnterScreen -> enterScreen()
//            else -> throw NotImplementedError("Invalid $intent for state $currentState")
//        }
//    }
//
//    private fun enterScreen() {
//        _viewState.value = AppSettingsViewState.Display
//    }
}