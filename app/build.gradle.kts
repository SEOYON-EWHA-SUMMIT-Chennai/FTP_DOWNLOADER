plugins {
    id("com.android.application") // Plugin for Android applications
    id("org.jetbrains.kotlin.android") // Plugin for Kotlin support in Android
}

android {
    namespace = "com.example.ftp_downloader" // Application's namespace
    compileSdk = 34 // SDK version to compile against
    defaultConfig {
        applicationId = "com.example.ftp_downloader" // Unique application ID
        minSdk = 26 // Minimum supported SDK version
        targetSdk = 34 // Target SDK version
        versionCode = 1 // Application's version code
        versionName = "1.0" // Application's version name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Default test runner
        vectorDrawables {
            useSupportLibrary = true // Use support library for vector drawables
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Disable code shrinking for release builds
            proguardFiles( // Proguard rules for release builds
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Source compatibility for Java 8
        targetCompatibility = JavaVersion.VERSION_1_8 // Target compatibility for Java 8
    }
    kotlinOptions {
        jvmTarget = "1.8" // Target JVM version for Kotlin
    }
    buildFeatures {
        compose = true // Enable Jetpack Compose
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Kotlin Compiler Extension Version for Compose
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Exclude META-INF files to avoid conflicts
        }
    }
}

dependencies {
    // Core Kotlin and Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Jetpack Compose dependencies
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // UI and compatibility dependencies
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // FTP Client Dependency
    implementation("commons-net:commons-net:3.9.0")
}
