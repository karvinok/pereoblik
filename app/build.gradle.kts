plugins {
    id("pereoblik.dependencies")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.vilinesoft.pereoblik"
    compileSdk = DependenciesPlugin.compileSdkVersion

    defaultConfig {
        applicationId = "com.vilinesoft.pereoblik"
        minSdk = DependenciesPlugin.minSdkVersion
        targetSdk = DependenciesPlugin.targetSdkVersion
        versionCode = DependenciesPlugin.versionCodeVersion
        versionName = DependenciesPlugin.versionNameVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = DependenciesPlugin.javaVersion
        targetCompatibility = DependenciesPlugin.javaVersion
    }
    kotlinOptions {
        jvmTarget = DependenciesPlugin.javaVersion.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesPlugin.composeCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    implementation(DependenciesPlugin.androidCore)
    implementation(DependenciesPlugin.koinCore)
    implementation(DependenciesPlugin.koinAndroid)
    implementation(DependenciesPlugin.androidLifecycle)
    implementation(DependenciesPlugin.navigationCompose)
    implementation(DependenciesPlugin.serialization)
    implementation(DependenciesPlugin.activityCompose)
}