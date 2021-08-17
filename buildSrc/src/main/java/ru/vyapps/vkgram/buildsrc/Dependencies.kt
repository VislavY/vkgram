package ru.vyapps.vkgram.buildsrc

@Suppress("Unused")
object Libs {

    object AndroidX {

        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Activity {

            const val version = "1.3.1"

            const val activityKtx = "androidx.activity:activity-ktx:$version"
            const val activityCompose = "androidx.activity:activity-compose:$version"
        }

        object Compose {

            const val version = "1.0.1"

            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val foundationLayout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        }

        object Hilt {

            const val version = "2.38.1"

            const val hiltAndroid = "com.google.dagger:hilt-android:$version"
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
            const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
        }

        object LifeCycle {

            const val lifeCycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
            const val lifeCycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        }

        object Navigation {

            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.0-alpha06"
        }


        object Vk {

            const val version = "3.2.0"

            const val androidSdkCore = "com.vk:android-sdk-core:$version"
            const val androidSdkApi = "com.vk:android-sdk-api:$version"
        }
    }
}