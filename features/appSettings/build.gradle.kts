import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.Version
    }
}

dependencies {
    api(project(":core"))
    api(project(":api"))

    api(Libs.AndroidX.CoreKtx)
    api(Libs.AndroidX.Appcompat)
    api(Libs.KotlinX.KotlinXCoroutinesCore)

    api(Libs.AndroidX.Compose.Material)
    api(Libs.AndroidX.Compose.MaterialIconsExtended)
    api(Libs.AndroidX.Navigation.NavigationCompose)
    api(Libs.Accompanist.AccompanistInsets)

    api(Libs.AndroidX.Hilt.HiltAndroid)
    kapt(Libs.AndroidX.Hilt.HiltCompiler)
}