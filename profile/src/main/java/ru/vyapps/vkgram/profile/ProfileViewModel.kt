package ru.vyapps.vkgram.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.EventHandler
import ru.vyapps.vkgram.profile.models.ProfileEvent
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.profile.repositories.AccountRepository
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel(), EventHandler<ProfileEvent> {

    private val _viewState = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: ProfileEvent) {
        when (val currentState = _viewState.value) {
            is ProfileViewState.Loading -> reduce(event, currentState)
            is ProfileViewState.Error -> reduce(event, currentState)
            is ProfileViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Loading) {
        when (event) {
            is ProfileEvent.EnterScreen -> getProfileInfo()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Error) {
        when (event) {
            is ProfileEvent.Reload -> getProfileInfo()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Display) {
        when (event) {
            is ProfileEvent.EnterScreen -> getProfileInfo()
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            try {
                val response = accountRepository.getProfileInfo()
                _viewState.value = ProfileViewState.Display(response)
            } catch (e: Exception) {
                Log.e(Tag, e.toString())
                _viewState.value = ProfileViewState.Error
            }
        }
    }

    companion object {
        private const val Tag = "Profile"
    }
}