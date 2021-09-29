import ru.vyapps.vkgram.buildsrc.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }

    dependencies {
        api(project(":core"))
        api(project(":api"))

        api(Libs.AndroidX.coreKtx)
        api(Libs.AndroidX.appcompat)
        api(Libs.KotlinX.kotlinXCoroutinesCore)

        api(Libs.AndroidX.Compose.material)
        api(Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(Libs.AndroidX.Navigation.navigationCompose)
        api(Libs.Google.accompanistSystemUiController)
        api(Libs.Coil.coilCompose)

        api(Libs.AndroidX.Hilt.hiltAndroid)
        kapt(Libs.AndroidX.Hilt.hiltCompiler)

        api(Libs.VK.androidSdkApi)
    }
}