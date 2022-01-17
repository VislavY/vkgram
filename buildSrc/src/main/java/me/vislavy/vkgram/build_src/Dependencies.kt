package me.vislavy.vkgram.build_src

@Suppress("UNUSED")
object Libs {
    object AndroidX {
        const val Appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val CoreKtx = "androidx.core:core-ktx:1.7.0"

        object Compose {
            const val Version = "1.1.0-rc01"
            const val Material = "androidx.compose.material:material:$Version"
            const val MaterialIconsExtended = "androidx.compose.material:material-icons-extended:$Version"
            const val Animation = "androidx.compose.animation:animation:$Version"
            const val UiTooling = "androidx.compose.ui:ui-tooling:$Version"
        }

        object Hilt {
            private const val Version = "2.40.5"
            const val HiltAndroid = "com.google.dagger:hilt-android:$Version"
            const val HiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
            const val HiltCompiler = "com.google.dagger:hilt-compiler:$Version"
        }

        object Lifecycle {
            const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"

        }

        object Navigation {
            const val NavigationCompose = "androidx.navigation:navigation-compose:2.4.0-rc01"
        }

        object Room {
            const val Version = "2.4.0"
            const val RoomRuntime = "androidx.room:room-runtime:$Version"
            const val RoomKtx = "androidx.room:room-ktx:$Version"
            const val RoomCompiler = "androidx.room:room-compiler:$Version"
        }

        object Datastore {
            const val DatastorePreferences = "androidx.datastore:datastore-preferences:1.0.0"
        }
    }

    object Accompanist {
        private const val Version = "0.22.0-rc"
        const val AccompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$Version"
        const val AccompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:$Version"
        const val AccompanistPlaceholder = "com.google.accompanist:accompanist-placeholder-material:$Version"
        const val AccompanistPermissions = "com.google.accompanist:accompanist-permissions:$Version"
        const val AccompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:$Version"
        const val AccompanistInsets = "com.google.accompanist:accompanist-insets:$Version"
        const val AccompanistPager = "com.google.accompanist:accompanist-pager:$Version"
    }

    object Coil {
        const val CoilCompose = "io.coil-kt:coil-compose:1.4.0"
    }

    object KotlinX {
        const val KotlinXCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
        const val KotlinXSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
    }

    object Retrofit {
        const val Retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val RetrofitKotlinXSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object VK {
        private const val Version = "3.2.0"
        const val AndroidSdkApi = "com.vk:android-sdk-api:$Version"
    }
}