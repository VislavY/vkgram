import ru.vyapps.vkgram.buildsrc.Libs

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

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }

    dependencies {
        api(project(":core"))
        api(project(":api"))

        api(Libs.AndroidX.coreKtx)
        api(Libs.AndroidX.appcompat)

        api(Libs.Google.accompanistPermissions)

        api(Libs.AndroidX.Navigation.navigationCompose)
        api(Libs.Google.accompanistNavigationAnimation)

        api(Libs.AndroidX.Compose.material)
        api(Libs.AndroidX.Compose.materialIconsExtended)
        api(Libs.AndroidX.Compose.animation)
        api(Libs.AndroidX.Compose.uiTooling)
        api(Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(Libs.Coil.coilCompose)

        api(Libs.AndroidX.Hilt.hiltAndroid)
        api(Libs.AndroidX.Hilt.hiltNavigationCompose)
        kapt(Libs.AndroidX.Hilt.hiltCompiler)

        api(Libs.KotlinX.kotlinXSerializationJson)
    }
}