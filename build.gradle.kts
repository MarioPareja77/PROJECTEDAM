import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.example.incidentmanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.incidentmanager"
        minSdk = 35
        multiDexEnabled = true

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.lifecycle.runtime.ktx.v251)
    implementation(libs.androidx.activity.compose.v161)

    // Jetpack Compose
    implementation(libs.ui)
    implementation(libs.androidx.ui.tooling.preview.v131)
    implementation(libs.androidx.material3.v101)
    implementation(libs.navigation.compose.v253)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.junit.ktx)

}
