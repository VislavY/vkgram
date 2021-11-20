package ru.vyapps.vkgram.core

import androidx.compose.runtime.compositionLocalOf
import ru.vyapps.vkgram.vk_api.data.User

val LocalProfile = compositionLocalOf<User> {
    error("No profile provided")
}