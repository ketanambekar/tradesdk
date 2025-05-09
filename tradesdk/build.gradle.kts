plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish") // Ensure the 'maven-publish' plugin is applied
}

android {
    namespace = "com.finoux.tradesdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Handle test and lint options
    testOptions {
        targetSdk = 34
    }

    lint {
        targetSdk = 34
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }}
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.material3:material3:1.2.1")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            // Use the correct component name for the AAR, usually 'release' or 'releaseAar'
//            from(components["release"])  // Correct component name
            groupId = "com.finoux"
            artifactId = "tradesdk"
            version = "1.0.1"
        }
    }
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

