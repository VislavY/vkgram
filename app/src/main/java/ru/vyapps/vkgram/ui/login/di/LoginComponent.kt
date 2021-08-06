package ru.vyapps.vkgram.ui.login.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.vyapps.vkgram.ui.login.LoginFragment
import ru.vyapps.vkgram.utils.FeatureScope

@Subcomponent(modules = [LoginBindModule::class])
@FeatureScope
interface LoginComponent {

    fun inject(fragment: LoginFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): LoginComponent
    }
}