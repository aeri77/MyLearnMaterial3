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
    val coreKTX by lazy { "androidx.core:core-ktx:${Versions.coreKTX}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val composeUI by lazy { "androidx.compose.ui:ui:${Versions.composeUi}" }
    val composeToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.composeUi}" }
    val composeJUnit by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.composeUi}" }
    val composeUITooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.composeUi}" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.composeUi}" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.composeMaterial3}" }
    val composeActivity by lazy { "androidx.activity:activity-compose:${Versions.composeActivity}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.composeUi}" }
    val composeIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.composeCompiler}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val navigationDynamic by lazy { "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigation}" }
    val navigationTesting by lazy { "androidx.navigation:navigation-testing:${Versions.navigation}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraint}" }
    val testJUnit by lazy { "androidx.test.ext:junit:1.1.3" }
    val testEspresso by lazy { "androidx.test.espresso:espresso-core:3.4.0" }


    object Accompanist {
        val pager by lazy { "com.google.accompanist:accompanist-pager:${Versions.Accompanist.version}" }
        val pagerIndicator by lazy { "com.google.accompanist:accompanist-pager-indicators:${Versions.Accompanist.version}" }
        val systemUIController by lazy { "com.google.accompanist:accompanist-systemuicontroller:${Versions.Accompanist.version}" }
        val navigationAnimation by lazy { "com.google.accompanist:accompanist-navigation-animation:${Versions.Accompanist.version}" }
        val flowLayout by lazy { "com.google.accompanist:accompanist-flowlayout:${Versions.Accompanist.version}" }
    }

    object DataStore {
        val datastore by lazy { "androidx.datastore:datastore:${Versions.Datastore.version}" }
        val core by lazy { "androidx.datastore:datastore-core:${Versions.Datastore.version}" }
    }

    object KTX {
        val activity by lazy { "androidx.activity:activity-ktx:${Versions.KTX.version}" }
        val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.KTX.version}" }
    }

    object Lifecycle {
        val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeUi}" }
        val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}" }
        val lifeCycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleRuntime}" }
    }

    object SquareUp {
        val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.SquareUp.version}" }
        val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.SquareUp.version}" }
        val httpLogging by lazy { "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3" }
    }

    object JakeWharton {
        val timber by lazy { "com.jakewharton.timber:timber:${Versions.JakeWharton.timber}" }
    }
}