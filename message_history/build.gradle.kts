plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
        api(me.vislavy.vkgram.build_src.Libs.KotlinX.kotlinXCoroutinesCore)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.materialIconsExtended)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.navigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Google.accompanistSystemUiController)
        api(me.vislavy.vkgram.build_src.Libs.Coil.coilCompose)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltAndroid)
        kapt(me.vislavy.vkgram.build_src.Libs.AndroidX.Hilt.hiltCompiler)
    }
}