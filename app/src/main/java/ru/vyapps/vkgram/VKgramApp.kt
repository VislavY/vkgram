package ru.vyapps.vkgram

import android.app.Application
import ru.vyapps.vkgram.di.AppComponent
import ru.vyapps.vkgram.di.DaggerAppComponent

class VKgramApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}