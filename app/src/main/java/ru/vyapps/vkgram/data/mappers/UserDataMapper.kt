package ru.vyapps.vkgram.data.mappers

import ru.vyapps.vkgram.vk_api.User
import ru.vyapps.vkgram.vk_api.UserData
import javax.inject.Inject

class UserDataMapper @Inject constructor() {

    fun map(input: UserData): User {
        return input.response.last()
    }
}