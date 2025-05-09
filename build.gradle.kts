// Project-level build.gradle.kts

buildscript {
    repositories {
        google()  // Add the Google repository
        mavenCentral()  // Add Maven Central repository
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0") // Ensure you're using the correct version
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22") // Ensure you're using the correct Kotlin version
    }
}

allprojects {
    repositories {
        google()  // Add the Google repository here as well
        mavenCentral()  // Add Maven Central repository
    }
}
