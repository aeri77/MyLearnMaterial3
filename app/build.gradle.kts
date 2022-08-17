import com.google.protobuf.gradle.*
import org.apache.tools.ant.util.JavaEnvUtils.JAVA_11

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf") version "0.8.12"
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.aeri77.mylearn"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JAVA_11)
        targetCompatibility(JAVA_11)
    }
    kotlinOptions {
        jvmTarget = JAVA_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")

    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}


dependencies {
    implementation(Deps.coreKTX)
    implementation(Deps.composeUI)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeMaterial3)
    implementation(Deps.composeToolingPreview)
    implementation(Deps.composeActivity)
    implementation(Deps.composeRuntimeLiveData)
    implementation(Deps.composeIconsExtended)
    implementation(Deps.composeIconsCore)
    implementation(Deps.Lifecycle.composeViewModel)
    implementation(Deps.Lifecycle.lifecycleRuntime)
    implementation(Deps.Lifecycle.lifeCycleViewModel)
    implementation(Deps.Hilt.hilt)
    implementation(Deps.Hilt.hiltGoogleCompiler)
    implementation(Deps.navigationDynamic)
    implementation(Deps.navigationCompose)
    implementation(Deps.navigationUI)
    implementation(Deps.navigationFragment)
    implementation(Deps.navigationHilt)
    implementation(Deps.Accompanist.pager)
    implementation(Deps.Accompanist.pagerIndicator)
    implementation(Deps.Accompanist.systemUIController)
    implementation(Deps.Accompanist.navigationAnimation)
    implementation(Deps.Accompanist.flowLayout)
    implementation(Deps.constraintLayout)
    implementation(Deps.DataStore.core)
    implementation(Deps.DataStore.datastore)
    implementation(Deps.KTX.activity)
    implementation(Deps.KTX.fragment)
    implementation(Deps.SquareUp.retrofit)
    implementation(Deps.SquareUp.gsonConverter)
    implementation(Deps.SquareUp.httpLogging)
    implementation(Deps.JakeWharton.timber)
    implementation(Deps.Google.protobuf)
    kapt(Deps.Hilt.hiltCompiler)
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.testJUnit)
    androidTestImplementation(Deps.testEspresso)
    androidTestImplementation(Deps.composeJUnit)
    androidTestImplementation(Deps.navigationTesting)
    debugImplementation(Deps.composeUITooling)
    debugImplementation(Deps.composeTestManifest)
}