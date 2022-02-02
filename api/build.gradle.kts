import me.vislavy.vkgram.build_src.Config
import me.vislavy.vkgram.build_src.Libs

plugins {
    id("com.android.library")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.CompileSdk

    defaultConfig {
        minSdk = Config.MinSdk
        targetSdk = Config.TargetSdk
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
    }
}

dependencies {
    api(Libs.AndroidX.CoreKtx)
    api(Libs.AndroidX.Appcompat)
    api(Libs.KotlinX.KotlinXCoroutinesCore)

    api(Libs.KotlinX.KotlinXSerializationJson)
    api("com.squareup.okhttp3:okhttp:4.9.1")
    api("ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0")
    api(Libs.Retrofit.Retrofit)
    api(Libs.Retrofit.RetrofitKotlinXSerializationConverter)

    api(Libs.AndroidX.Room.RoomRuntime)
    api(Libs.AndroidX.Room.RoomKtx)
    kapt(Libs.AndroidX.Room.RoomCompiler)
}