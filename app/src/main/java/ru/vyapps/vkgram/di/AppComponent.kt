package ru.vyapps.vkgram.di

import dagger.Component
import dagger.Module
import ru.vyapps.vkgram.ui.login.di.LoginComponent
import ru.vyapps.vkgram.di.scopes.AppScope
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponent::class])
@AppScope
interface AppComponent {

    val loginComponent: LoginComponent.Builder
}

@Module(subcomponents = [LoginComponent::class])
object AppSubcomponent