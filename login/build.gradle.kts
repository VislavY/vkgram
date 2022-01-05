plugins {
    id("com.android.library")
    id("kotlin-android")
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

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }

    dependencies {
        api(project(":core"))

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.CoreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Appcompat)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.LifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.NavigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Accompanist.AccompanistSystemUiController)

        api(me.vislavy.vkgram.build_src.Libs.VK.AndroidSdkApi)
    }

    composeOptions {
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Version
    }
}