import ru.vyapps.vkgram.buildsrc.Libs

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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }

    dependencies {
        api(project(":api"))

        api(Libs.AndroidX.coreKtx)
        api(Libs.AndroidX.appcompat)

        api(Libs.AndroidX.Compose.material)

        api(Libs.AndroidX.Hilt.hiltAndroid)
        kapt(Libs.AndroidX.Hilt.hiltCompiler)
    }
}