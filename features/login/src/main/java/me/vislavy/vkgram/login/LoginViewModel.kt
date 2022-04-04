package me.vislavy.vkgram.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.vislavy.vkgram.core.base.MviViewModel
import me.vislavy.vkgram.login.models.LoginEvent
import me.vislavy.vkgram.login.models.LoginViewState

class LoginViewModel : ViewModel(), MviViewModel<LoginEvent> {

    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Display)
    val viewState = _viewState.asStateFlow()

    override fun onEvent(event: LoginEvent) {
        when (val currentState = _viewState.value) {
            is LoginViewState.Loading -> Any()
            is LoginViewState.Display -> reduce(event, currentState)
        }
    }

    private fun reduce(event: LoginEvent, currentState: LoginViewState.Display) {
        when (event) {
            is LoginEvent.OnLoginButtonClick -> login(event.activity)
        }
    }

    private fun login(activity: Activity) {
        val scopes = listOf(
            VKScope.MESSAGES,
            VKScope.FRIENDS,
            VKScope.PHOTOS
        )
        VK.login(activity, scopes)

        _viewState.value = LoginViewState.Loading
    }
}