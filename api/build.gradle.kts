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
    api(me.vislavy.vkgram.build_src.Libs.AndroidX.coreKtx)
    api(me.vislavy.vkgram.build_src.Libs.AndroidX.appcompat)
    api(me.vislavy.vkgram.build_src.Libs.KotlinX.kotlinXCoroutinesCore)

    api(me.vislavy.vkgram.build_src.Libs.KotlinX.kotlinXSerializationJson)
    api("com.squareup.okhttp3:okhttp:4.9.1")
    api("ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0")
    api(me.vislavy.vkgram.build_src.Libs.Retrofit.retrofit)
    api(me.vislavy.vkgram.build_src.Libs.Retrofit.retrofitKotlinXSerializationConverter)
}