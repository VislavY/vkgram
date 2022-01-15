package me.vislavy.vkgram.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.IntentHandler
import me.vislavy.vkgram.core.repositories.UserRepository
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileViewState
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), IntentHandler<ProfileEvent> {

    private val _viewState = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)
    val viewState = _viewState.asStateFlow()

    override fun onIntent(intent: ProfileEvent) {
        when (val currentState = _viewState.value) {
            is ProfileViewState.Loading -> reduce(intent, currentState)
            is ProfileViewState.Error -> reduce(intent, currentState)
            is ProfileViewState.Display -> reduce(intent, currentState)
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Loading) {
        when (event) {
            is ProfileEvent.EnterScreen -> getUserById(event.userId)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Error) {
        when (event) {
            is ProfileEvent.Reload -> getUserById(event.userId)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun reduce(event: ProfileEvent, currentState: ProfileViewState.Display) {
        when (event) {
            is ProfileEvent.EnterScreen -> getUserById(event.userId)
            else -> throw NotImplementedError("Invalid $event for state $currentState")
        }
    }

    private fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserListById(listOf(id)).first()
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