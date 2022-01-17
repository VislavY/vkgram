plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
        api(me.vislavy.vkgram.build_src.Libs.KotlinX.KotlinXCoroutinesCore)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.MaterialIconsExtended)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.LifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.NavigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistSystemUiController)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistPager)
        api(me.vislavy.vkgram.build_src.Libs.Coil.CoilCompose)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistInsets)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistInsetsUi)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltAndroid)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltCompiler)

        api(me.vislavy.vkgram.build_src.Libs.VK.AndroidSdkApi)
    }
}