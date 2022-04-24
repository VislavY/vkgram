package me.vislavy.vkgram.build_src

@Suppress("UNUSED")
object Libs {
    object AndroidX {
        const val Appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val CoreKtx = "androidx.core:core-ktx:1.7.0"
        const val Palette = "androidx.palette:palette:1.0.0"

        object Compose {
            const val Version = "1.2.0-alpha07"
            const val Material = "androidx.compose.material:material:$Version"
            const val MaterialIconsExtended = "androidx.compose.material:material-icons-extended:$Version"
            const val Material3 = "androidx.compose.material3:material3:1.0.0-alpha09"
            const val Animation = "androidx.compose.animation:animation:$Version"
            const val UiTooling = "androidx.compose.ui:ui-tooling:$Version"
            const val UiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$Version"
        }

        object Lifecycle {
            const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"

        }

        object Navigation {
            const val NavigationCompose = "androidx.navigation:navigation-compose:2.4.2"
        }

        object Room {
            const val Version = "2.4.2"
            const val RoomRuntime = "androidx.room:room-runtime:$Version"
            const val RoomKtx = "androidx.room:room-ktx:$Version"
            const val RoomCompiler = "androidx.room:room-compiler:$Version"
        }

        object Datastore {
            const val DatastorePreferences = "androidx.datastore:datastore-preferences:1.0.0"
        }
    }

    object Coil {
        const val CoilCompose = "io.coil-kt:coil-compose:2.0.0-rc03"
    }

    object KotlinX {
        const val KotlinXCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
        const val KotlinXSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
    }

    object Retrofit {
        const val Retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val RetrofitKotlinXSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object Google {
        object Hilt {
            const val Version = "2.41"
            const val HiltAndroid = "com.google.dagger:hilt-android:$Version"
            const val HiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
            const val HiltCompiler = "com.google.dagger:hilt-compiler:$Version"
        }

        object Accompanist {
            private const val Version = "0.24.6-alpha"
            const val AccompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$Version"
            const val AccompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:$Version"
            const val AccompanistPlaceholder = "com.google.accompanist:accompanist-placeholder-material:$Version"
            const val AccompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$Version"
            const val AccompanistPermissions = "com.google.accompanist:accompanist-permissions:$Version"
            const val AccompanistFlowLayout = "com.google.accompanist:accompanist-flowlayout:$Version"
            const val AccompanistPager = "com.google.accompanist:accompanist-pager:$Version"
        }
    }
}
