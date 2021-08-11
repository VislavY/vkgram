object Version {

    const val ANDROIDX_CORE = "1.6.0"
    const val ANDROIDX_APPCOMPAT = "1.3.1"
    const val ANDROIDX_CONSTRAINTLAYOUT = "2.0.4"
    const val ANDROIDX_NAVIGATION = "2.3.5"

    const val GOOGLE_MATERIAL = "1.4.0"
    const val GOOGLE_DAGGER = "2.38.1"

    const val VK_SDK = "2.2.0"

    const val JUNIT = "4.13.2"
    const val ANDROIDX_TEST_JUNIT = "1.1.3"
    const val ANDROIDX_TEST_ESPRESSO = "3.4.0"
}

@Suppress("unused")
object Depend {

    const val ANDROIDX_CORE = "androidx.core:core-ktx:${Version.ANDROIDX_CORE}"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Version.ANDROIDX_APPCOMPAT}"
    const val ANDROIDX_CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:${Version.ANDROIDX_CONSTRAINTLAYOUT}"
    const val ANDROIDX_NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Version.ANDROIDX_NAVIGATION}"
    const val ANDROIDX_NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Version.ANDROIDX_NAVIGATION}"

    const val GOOGLE_MATERIAL = "com.google.android.material:material:${Version.GOOGLE_MATERIAL}"
    const val GOOGLE_DAGGER = "com.google.dagger:dagger:${Version.GOOGLE_DAGGER}"
    const val GOOGLE_DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Version.GOOGLE_DAGGER}"

    const val VK_SDK = "com.vk:androidsdk:${Version.VK_SDK}"

    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val ANDROIDX_TEST_JUNIT = "androidx.test.ext:junit:${Version.ANDROIDX_TEST_JUNIT}"
    const val ANDROIDX_TEST_ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ANDROIDX_TEST_ESPRESSO}"
}