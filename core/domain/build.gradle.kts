plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("pereoblik.dependencies")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.vilinesoft.domain"
    compileSdk = DependenciesPlugin.compileSdkVersion
    compileOptions {
        sourceCompatibility = DependenciesPlugin.javaVersion
        targetCompatibility = DependenciesPlugin.javaVersion
    }
    kotlinOptions {
        jvmTarget = DependenciesPlugin.javaVersion.toString()
    }
}

dependencies {
    implementation(DependenciesPlugin.immutableCollections)

    //TODO replace compose runtime by mapping
    // unstable elements from domain to UI feature module.
    implementation(DependenciesPlugin.composeRuntime)
}