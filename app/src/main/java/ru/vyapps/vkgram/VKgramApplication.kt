package ru.vyapps.vkgram

import android.app.Application
import ru.vyapps.vkgram.di.ApplicationComponent
import ru.vyapps.vkgram.di.DaggerApplicationComponent

class VKgramApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.create()
    }
}