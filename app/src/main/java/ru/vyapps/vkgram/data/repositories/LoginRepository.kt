package ru.vyapps.vkgram.data.repositories

import android.app.Activity

interface LoginRepository {

    fun login(activity: Activity)
}