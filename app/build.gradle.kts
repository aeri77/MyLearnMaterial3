import org.apache.tools.ant.util.JavaEnvUtils.JAVA_11
import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    }
}

dependencies {
    implementation(Deps.coreKTX)
    implementation(Deps.composeUI)
    implementation(Deps.composeMaterial3)
    implementation(Deps.composeToolingPreview)
    implementation(Deps.composeActivity)
    implementation(Deps.composeViewModel)
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.hilt)
    implementation(Deps.navigationDynamic)
    implementation(Deps.navigationCompose)
    implementation(Deps.navigationUI)
    implementation(Deps.navigationFragment)
    implementation(Deps.Accompanist.pager)
    implementation(Deps.Accompanist.pagerIndicator)
    implementation(Deps.Accompanist.systemUIController)
    implementation(Deps.constraintLayout)
    kapt(Deps.hiltCompiler)
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.testJUnit)
    androidTestImplementation(Deps.testEspresso)
    androidTestImplementation(Deps.composeJUnit)
    androidTestImplementation(Deps.navigationTesting)
    debugImplementation(Deps.composeUITooling)
    debugImplementation(Deps.composeTestManifest)
}