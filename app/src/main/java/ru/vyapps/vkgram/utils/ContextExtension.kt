package ru.vyapps.vkgram.utils

import android.content.Context
import ru.vyapps.vkgram.VKgramApp
import ru.vyapps.vkgram.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is VKgramApp -> appComponent
        else -> applicationContext.appComponent
    }