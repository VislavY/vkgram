object Versions {

    const val ANDROIDX_CORE = "1.6.0"
    const val ANDROIDX_APPCOMPAT = "1.3.1"
    const val ANDROIDX_CONSTRAINTLAYOUT = "2.0.4"
    const val ANDROIDX_NAVIGATION = "2.3.5"

    const val GOOGLE_MATERIAL = "1.4.0"
    const val GOOGLE_DAGGER = "2.38.1"

    const val SQUAREUP_RETROFIT = "2.9.0"

    const val VK_SDK = "2.2.0"

    const val JUNIT = "4.13.2"
    const val ANDROIDX_TEST_JUNIT = "1.1.3"
    const val ANDROIDX_TEST_ESPRESSO = "3.4.0"
}

@Suppress("unused")
object Depends {

    const val ANDROIDX_CORE = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"
    const val ANDROIDX_CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINTLAYOUT}"
    const val ANDROIDX_NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION}"
    const val ANDROIDX_NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION}"

    const val GOOGLE_MATERIAL = "com.google.android.material:material:${Versions.GOOGLE_MATERIAL}"
    const val GOOGLE_DAGGER = "com.google.dagger:dagger:${Versions.GOOGLE_DAGGER}"
    const val GOOGLE_DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.GOOGLE_DAGGER}"

    const val SQUAREUP_RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.SQUAREUP_RETROFIT}"

    const val VK_SDK = "com.vk:androidsdk:${Versions.VK_SDK}"

    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val ANDROIDX_TEST_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_JUNIT}"
    const val ANDROIDX_TEST_ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_TEST_ESPRESSO}"
}