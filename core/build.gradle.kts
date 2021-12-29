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
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.version
    }

    dependencies {
        api(project(":api"))

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.coreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.appcompat)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.uiTooling)

        api(me.vislavy.vkgram.build_src.Libs.KotlinX.kotlinXSerializationJson)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltAndroid)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltCompiler)
    }
}