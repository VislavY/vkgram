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
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Version
    }

    dependencies {
        api(project(":core"))
        api(project(":api"))

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.CoreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Appcompat)

        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistPermissions)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.NavigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistNavigationAnimation)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.MaterialIconsExtended)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Animation)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.UiTooling)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.LifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.Coil.CoilCompose)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltAndroid)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltNavigationCompose)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltCompiler)

        api(me.vislavy.vkgram.build_src.Libs.KotlinX.KotlinXSerializationJson)
    }
}