package ru.vyapps.vkgram.buildsrc

@Suppress("UNUSED")
object Libs {
    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Compose {
            const val version = "1.1.0-beta01"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        }

        object Hilt {
            private const val version = "2.38.1"
            const val hiltAndroid = "com.google.dagger:hilt-android:$version"
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
            const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
        }

        object Lifecycle {
            const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"

        }

        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.0-beta01"
        }
    }

    object Google {
        private const val version = "0.21.0-beta"
        const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
        const val accompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:$version"
        const val accompanistPager = "com.google.accompanist:accompanist-pager:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.4.0"
    }

    object KotlinX {
        const val kotlinXCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
        const val kotlinXSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitKotlinXSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object VK {
        private const val version = "3.2.0"
        const val androidSdkApi = "com.vk:android-sdk-api:$version"
    }
}