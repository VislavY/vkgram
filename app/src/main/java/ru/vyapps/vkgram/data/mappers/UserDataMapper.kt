package ru.vyapps.vkgram.data.mappers

import ru.vyapps.vkgram.data.remote.User
import ru.vyapps.vkgram.data.remote.UserData
import javax.inject.Inject

class UserDataMapper @Inject constructor() {

    fun map(input: UserData): User {
        return input.response.last()
    }
}