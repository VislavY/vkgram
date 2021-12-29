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

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.coreKtx)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.appcompat)

        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.material)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Lifecycle.lifecycleViewModelCompose)
        api(me.vislavy.vkgram.build_src.Libs.AndroidX.Navigation.navigationCompose)
        api(me.vislavy.vkgram.build_src.Libs.Google.accompanistSystemUiController)

        api(me.vislavy.vkgram.build_src.Libs.VK.androidSdkApi)
    }

    composeOptions {
        kotlinCompilerExtensionVersion = me.vislavy.vkgram.build_src.Libs.AndroidX.Compose.version
    }
}