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
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Version
    }

    dependencies {
        api(project(":api"))

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.CoreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Appcompat)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.Material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.UiTooling)

        api(me.vislavy.vkgram.build_src.Libs.KotlinX.KotlinXSerializationJson)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltAndroid)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.HiltCompiler)
    }
}