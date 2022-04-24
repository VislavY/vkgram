import me.vislavy.vkgram.build_src.Config
import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.CompileSdk

    defaultConfig {
        minSdk = Config.MinSdk
        targetSdk = Config.TargetSdk

        resourceConfigurations.addAll(listOf("ru", "en"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.Version
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }
}

dependencies {
    api(project(":core"))
    api(project(":api"))

    api(Libs.AndroidX.CoreKtx)
    api(Libs.AndroidX.Appcompat)
    api(Libs.KotlinX.KotlinXCoroutinesCore)

    api(Libs.AndroidX.Compose.Material)
    api(Libs.AndroidX.Navigation.NavigationCompose)
    api(Libs.Google.Accompanist.AccompanistPlaceholder)
    api(Libs.Coil.CoilCompose)

    api(Libs.Google.Hilt.HiltAndroid)
    kapt(Libs.Google.Hilt.HiltCompiler)
}