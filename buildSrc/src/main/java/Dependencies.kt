@file:Suppress("unused")

object AndroidDependencies {

    const val coreLib = "androidx.core:core-ktx:${Versions.coreVersion}"
    const val appcompatLib = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val coroutinesLib =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val lifecycleKtxLib =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"

    // UI
    const val materialLib = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLib =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintVersion}"

    // Navigation
    const val navFragmentLib = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navUiLib = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"
}

object KtDependencies {
    const val ktStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.ktVersion}"
}

object RoomDependencies {
    const val roomRuntimeLib = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompilerLib = "androidx.room:room-compiler:${Versions.roomVersion}"
}

object HiltDependencies {
    const val hiltAndroidLib = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompilerLib = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
}

object KtResultDependencies {
    const val ktResultLib =
        "com.michael-bull.kotlin-result:kotlin-result:${Versions.ktResultVersion}"
    const val ktResultCoroutinesLib =
        "com.michael-bull.kotlin-result:kotlin-result-coroutines:${Versions.ktResultVersion}"
}

object LintDependencies {
    const val lintApiLib =
        "com.android.tools.lint:lint-api:${Versions.lintVersion}"
    const val lintChecksLib = "com.android.tools.lint:lint-checks:${Versions.lintVersion}"
    const val lintLib = "com.android.tools.lint:lint:${Versions.lintVersion}"
    const val lintTestsLib = "com.android.tools.lint:lint-tests:${Versions.lintVersion}"
}

object LeakCanaryDependencies {
    const val leakcanaryLib =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanaryVersion}"
}

object TimberDependencies {
    const val timberLib = "com.jakewharton.timber:timber:${Versions.timberVersion}"
}

object TestDependencies {
    const val junitLib = "junit:junit:${Versions.junitVersion}"
}
