plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.finoux.tradesdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                // Ensure the correct AAR component is selected
                from(components["release"])

                groupId = "com.github.ketanambekar" // Or your desired groupId
                artifactId = "tradesdk" // Your library name
                version = "1.0.3" // Your library version
            }
        }

        repositories {
            maven {
                url = uri("https://jitpack.io") // Target JitPack repository URL
            }
        }
    }
}
