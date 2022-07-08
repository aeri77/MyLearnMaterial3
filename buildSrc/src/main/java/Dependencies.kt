/**
 * To define plugins
 */
object BuildPlugins {
    /**
    * Example val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    **/
}

/**
 * To define dependencies
 */
object Deps {
    val coreKTX by lazy {"androidx.core:core-ktx:${Versions.coreKTX}"}
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val composeUI by lazy {"androidx.compose.ui:ui:${Versions.compose}"}
    val composeToolingPreview by lazy {"androidx.compose.ui:ui-tooling-preview:${Versions.compose}"}
    val composeJUnit by lazy {"androidx.compose.ui:ui-test-junit4:${Versions.compose}"}
    val composeUITooling by lazy {"androidx.compose.ui:ui-tooling:${Versions.compose}"}
    val composeTestManifest by lazy {"androidx.compose.ui:ui-test-manifest:${Versions.compose}"}
    val composeMaterial3 by lazy {"androidx.compose.material3:material3:${Versions.composeMaterial3}"}
    val composeActivity by lazy {"androidx.activity:activity-compose:${Versions.composeActivity}"}
    val lifecycleRuntime by lazy {"androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}"}
    val hilt by lazy {"com.google.dagger:hilt-android:${Versions.hilt}"}
    val hiltCompiler by lazy {"com.google.dagger:hilt-android-compiler:${Versions.hilt}"}
    val testJUnit by lazy {"androidx.test.ext:junit:1.1.3"}
    val testEspresso by lazy {"androidx.test.espresso:espresso-core:3.4.0"}
}