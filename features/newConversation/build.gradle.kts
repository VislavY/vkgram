import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.Version
    }

    dependencies {
        api(project(":core"))
        api(project(":api"))

        api(Libs.AndroidX.CoreKtx)
        api(Libs.AndroidX.Appcompat)

        api(Libs.Accompanist.AccompanistPermissions)

        api(Libs.AndroidX.Navigation.NavigationCompose)
        api(Libs.Accompanist.AccompanistNavigationAnimation)

        api(Libs.AndroidX.Compose.Material)
        api(Libs.AndroidX.Compose.MaterialIconsExtended)
        api(Libs.AndroidX.Compose.Animation)
        api(Libs.AndroidX.Compose.UiTooling)
        api(Libs.AndroidX.Lifecycle.LifecycleViewModelCompose)
        api(Libs.Accompanist.AccompanistInsets)
        api(Libs.Accompanist.AccompanistInsetsUi)
        api(Libs.Coil.CoilCompose)

        api(Libs.AndroidX.Hilt.HiltAndroid)
        api(Libs.AndroidX.Hilt.HiltNavigationCompose)
        kapt(Libs.AndroidX.Hilt.HiltCompiler)

        api(Libs.KotlinX.KotlinXSerializationJson)
    }
}