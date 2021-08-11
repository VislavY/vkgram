package ru.vyapps.vkgram.data.repositories

interface LoginRepository {

    fun login()

    fun isLoggedIn(): Boolean
}