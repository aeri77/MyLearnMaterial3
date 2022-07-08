import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
        sourceCompatibility(VERSION_11)
        targetCompatibility(VERSION_11)
    }
    kotlinOptions {
        jvmTarget = "11"
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
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.hilt)
    implementation(Deps.hiltCompiler)
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.testJUnit)
    androidTestImplementation(Deps.testEspresso)
    androidTestImplementation(Deps.composeJUnit)
    debugImplementation(Deps.composeUITooling)
    debugImplementation(Deps.composeTestManifest)
}