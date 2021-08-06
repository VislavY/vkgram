package ru.vyapps.vkgram.utils

import android.content.Context
import ru.vyapps.vkgram.VKgramApplication
import ru.vyapps.vkgram.di.ApplicationComponent

val Context.applicationComponent: ApplicationComponent
    get() = when (this) {
        is VKgramApplication -> applicationComponent
        else -> applicationContext.applicationComponent
    }