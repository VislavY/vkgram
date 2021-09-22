import ru.vyapps.vkgram.buildsrc.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
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

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }
}

dependencies {
    api(Libs.AndroidX.coreKtx)
    api(Libs.AndroidX.appcompat)

    api(Libs.KotlinX.kotlinXSerializationJson)
    api(Libs.Retrofit.retrofit)
    api(Libs.Retrofit.retrofitKotlinXSerializationConverter)
}