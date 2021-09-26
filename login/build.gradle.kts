import ru.vyapps.vkgram.buildsrc.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
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

    dependencies {
        api(project(":core"))

        api(Libs.AndroidX.coreKtx)
        api(Libs.AndroidX.appcompat)

        api(Libs.AndroidX.Compose.material)
        api(Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(Libs.AndroidX.Navigation.navigationCompose)
        api(Libs.Google.accompanistSystemUiController)

        api(Libs.VK.androidSdkApi)
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }
}