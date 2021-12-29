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
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.version
    }

    dependencies {
        api(project(":core"))
        api(project(":api"))

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.coreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.appcompat)

        api(me.vislavy.vkgram.build_src.Libs.Google.accompanistPermissions)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.navigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Google.accompanistNavigationAnimation)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.materialIconsExtended)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.animation)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.uiTooling)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.Coil.coilCompose)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltAndroid)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltNavigationCompose)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltCompiler)

        api(me.vislavy.vkgram.build_src.Libs.KotlinX.kotlinXSerializationJson)
    }
}