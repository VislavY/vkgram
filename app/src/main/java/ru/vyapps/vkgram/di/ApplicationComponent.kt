package ru.vyapps.vkgram.di

import dagger.Component
import dagger.Module
import ru.vyapps.vkgram.ui.login.di.LoginComponent
import ru.vyapps.vkgram.utils.ApplicationScope

@Component(modules = [ApplicationSubcomponent::class])
@ApplicationScope
interface ApplicationComponent {

    val loginComponent: LoginComponent.Builder
}

@Module(subcomponents = [LoginComponent::class])
object ApplicationSubcomponent