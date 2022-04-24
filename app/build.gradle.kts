import me.vislavy.vkgram.build_src.Config
import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.application")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.CompileSdk

    defaultConfig {
        applicationId = Config.AppId
        minSdk = Config.MinSdk
        targetSdk = Config.TargetSdk
        versionCode = Config.VersionCode
        versionName = Config.VersionName

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
    api(project(":api"))
    api(project(":features:login"))
    api(project(":features:home"))
    api(project(":features:messageHistory"))
    api(project(":features:newConversation"))
    api(project(":features:profile"))
    api(project(":features:search"))
    api(project(":features:appSettings"))

    api(Libs.AndroidX.CoreKtx)
    api(Libs.AndroidX.Appcompat)

    api(Libs.AndroidX.Navigation.NavigationCompose)
    api(Libs.Google.Accompanist.AccompanistNavigationAnimation)
    api(Libs.AndroidX.Datastore.DatastorePreferences)

    api(Libs.Google.Accompanist.AccompanistSystemUiController)

    api(Libs.Google.Hilt.HiltAndroid)
    api(Libs.Google.Hilt.HiltNavigationCompose)
    kapt(Libs.Google.Hilt.HiltCompiler)
}