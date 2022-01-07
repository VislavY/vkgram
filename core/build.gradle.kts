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

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.Version
    }

    dependencies {
        api(project(":api"))

        api(Libs.AndroidX.CoreKtx)
        api(Libs.AndroidX.Appcompat)

        api(Libs.AndroidX.Compose.Material)
        api(Libs.AndroidX.Compose.UiTooling)

        api(Libs.KotlinX.KotlinXSerializationJson)

        api(Libs.AndroidX.Hilt.HiltAndroid)
        kapt(Libs.AndroidX.Hilt.HiltCompiler)

        api(Libs.AndroidX.Datastore.DatastorePreferences)
    }
}