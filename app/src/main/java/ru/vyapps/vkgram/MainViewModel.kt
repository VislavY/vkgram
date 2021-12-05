package ru.vyapps.vkgram

import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import ru.vyapps.vkgram.core.repositories.UserRepository
import ru.vyapps.vkgram.vk_api.data.User
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    val profile = flow {
        val response =
            userRepository.getUserListById(listOf(VK.getUserId()))
                .first()
//            User(1, "", "", "")
        emit(response)
    }

    init {

    }
}