import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "mx.pgdz.testsdk"
    compileSdk = 36

    defaultConfig {
        applicationId = "mx.pgdz.testsdk"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Leer claves desde gradle.properties
        var encryptionKey: String = project.findProperty("MODEL_ENCRYPTION_KEY") as? String ?: ""
        var encryptionIV: String = project.findProperty("MODEL_ENCRYPTION_IV") as? String ?: ""

        // Si no están, intentar leer desde local.properties
        if (encryptionKey.isEmpty() || encryptionIV.isEmpty()) {
            val localProps = Properties()
            val localPropsFile = rootProject.file("local.properties")

            if (localPropsFile.exists()) {
                localProps.load(localPropsFile.inputStream())

                if (encryptionKey.isEmpty()) {
                    encryptionKey = localProps.getProperty("MODEL_ENCRYPTION_KEY") ?: ""
                }
                if (encryptionIV.isEmpty()) {
                    encryptionIV = localProps.getProperty("MODEL_ENCRYPTION_IV") ?: ""
                }
            }
        }

        // buildConfigField en Kotlin DSL
        buildConfigField("String", "MODEL_ENCRYPTION_KEY", "\"$encryptionKey\"")
        buildConfigField("String", "MODEL_ENCRYPTION_IV", "\"$encryptionIV\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}