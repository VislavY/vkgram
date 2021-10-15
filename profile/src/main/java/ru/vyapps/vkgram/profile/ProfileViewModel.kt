package ru.vyapps.vkgram.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vk.api.sdk.VK
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.flow
import ru.vyapps.vkgram.core.repositories.UserRepo

class ProfileViewModel @AssistedInject constructor(
    @Assisted private val accessToken: String,
    private val userRepo: UserRepo
) : ViewModel() {

    val profile = flow {
        val receivedProfile = userRepo
            .getUsersById(accessToken, VK.getUserId())
            .response
            .first()
        emit(receivedProfile)
    }

    @AssistedFactory
    interface Factory {

        fun create(accessToken: String): ProfileViewModel
    }

    companion object {

        fun provideFactory(
            factory: Factory,
            accessToken: String
        ) = object: ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return  factory.create(accessToken) as T
            }
        }
    }
}