import me.vislavy.vkgram.build_src.Config
import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.CompileSdk

    defaultConfig {
        minSdk = Config.MinSdk
        targetSdk = Config.TargetSdk
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
    api(project(":api"))

    api(Libs.AndroidX.CoreKtx)
    api(Libs.AndroidX.Appcompat)
    api(Libs.KotlinX.KotlinXCoroutinesCore)

    api(Libs.AndroidX.Compose.Material)
    api(Libs.AndroidX.Compose.MaterialIconsExtended)
    api(Libs.AndroidX.Compose.Material3)
    debugImplementation(Libs.AndroidX.Compose.UiTooling)
    api(Libs.AndroidX.Compose.UiToolingPreview)
    api(Libs.Google.Accompanist.AccompanistPlaceholder)

    api(Libs.AndroidX.Lifecycle.LifecycleViewModelCompose)
    api(Libs.AndroidX.Navigation.NavigationCompose)

    api(Libs.Google.Hilt.HiltAndroid)
    kapt(Libs.Google.Hilt.HiltCompiler)

    api(Libs.AndroidX.Datastore.DatastorePreferences)
    api(Libs.KotlinX.KotlinXSerializationJson)

    api("com.vk:android-sdk-core:3.5.2")
    api("com.vk:android-sdk-api:3.5.2")
}