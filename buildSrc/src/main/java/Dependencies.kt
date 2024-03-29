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
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.composeCompiler}" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.composeMaterial3}" }
    val composeActivity by lazy { "androidx.activity:activity-compose:${Versions.composeActivity}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.composeUi}" }
    val composeIconsCore by lazy { "androidx.compose.material:material-icons-core:${Versions.composeCompiler}" }
    val composeIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.composeCompiler}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val navigationDynamic by lazy { "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigation}" }
    val navigationHilt by lazy { "androidx.hilt:hilt-navigation-compose:1.0.0" }
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
        val permissions by lazy { "com.google.accompanist:accompanist-permissions:${Versions.Accompanist.version}" }
        val swipeRefresh by lazy { "com.google.accompanist:accompanist-swiperefresh:${Versions.Accompanist.version}" }
    }

    object AndroidX {
        val security by lazy { "androidx.security:security-crypto:1.0.0" }

        object Paging {
            val paging by lazy { "androidx.paging:paging-runtime:3.1.1" }
            val pagingCompose by lazy { "androidx.paging:paging-compose:1.0.0-alpha16" }
            val pagingCommon by lazy {"androidx.paging:paging-common-ktx:3.1.1"}
        }

        object Room {
            val runtime by lazy { "androidx.room:room-runtime:${Versions.AndroidX.Room.version}" }
            val compiler by lazy { "androidx.room:room-compiler:${Versions.AndroidX.Room.version}" }
            val ktx by lazy { "androidx.room:room-ktx:${Versions.AndroidX.Room.version}" }
            val paging by lazy { "androidx.room:room-paging:${Versions.AndroidX.Room.version}" }
        }
    }

    object Hilt {
        val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
        val hiltGoogleCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
        val hiltViewModel by lazy { "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.version}" }
        val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.Hilt.version}" }
    }

    object Google {
        val protobuf by lazy { "com.google.protobuf:protobuf-javalite:${Versions.Google.javaLiteVersion}" }
        val crypto by lazy { "com.google.crypto.tink:tink-android:1.6.1" }
        val safetyNet by lazy { "com.google.android.gms:play-services-safetynet:18.0.1" }
        val locationServices by lazy { "com.google.android.gms:play-services-location:20.0.0" }
        val auth by lazy { "com.google.android.gms:play-services-auth:20.3.0" }

        object Firebase {
            val bom by lazy { "com.google.firebase:firebase-bom:30.4.1" }
            val analytic by lazy { "com.google.firebase:firebase-analytics-ktx" }
            val crashlytics by lazy { "com.google.firebase:firebase-crashlytics" }
            val cloudMessaging by lazy { "com.google.firebase:firebase-messaging"}
        }
        object Map {
            val mapsUtil by lazy {  "com.google.maps.android:android-maps-utils:2.3.0" }
            val mapsCompose by lazy { "com.google.maps.android:maps-compose:2.5.3" }
            val maps by lazy { "com.google.android.gms:play-services-maps:${Versions.Google.playServices}" }
        }
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

    object KotlinX {
        val serializationJson by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinX.serializationVer}" }
        val serializationProtobuf by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${Versions.KotlinX.serializationVer}" }
    }

    object JakeWharton {
        val timber by lazy { "com.jakewharton.timber:timber:${Versions.JakeWharton.timber}" }
        val serializerConverter by lazy { "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.JakeWharton.converter}" }
    }
    object ChrisBane {
        val snapper by lazy { "dev.chrisbanes.snapper:snapper:0.3.0" }
    }
}