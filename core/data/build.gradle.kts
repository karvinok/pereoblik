plugins {
    kotlin("android")
    id("com.android.library")
    id("pereoblik.dependencies")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.vilinesoft.data"
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
    implementation(project(":core:domain"))
    
    implementation(DependenciesPlugin.koinCore)
    implementation(DependenciesPlugin.koinAndroid)

    implementation(DependenciesPlugin.roomRuntime)
    implementation(DependenciesPlugin.roomKsp) {
        exclude(group = "com.intellij", module = "annotations")
    }

    ksp(DependenciesPlugin.roomKsp)

    implementation(DependenciesPlugin.serialization)
    implementation(DependenciesPlugin.retrofit)
    implementation(DependenciesPlugin.retrofitSerialization)
    implementation(DependenciesPlugin.loggingInterceptor)
    implementation(DependenciesPlugin.timber)
}