plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("pereoblik.dependencies")
}

android {
    namespace = "com.vilinesoft.navigation"
    compileSdk = DependenciesPlugin.compileSdkVersion
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
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":feature:home"))
    implementation(project(":feature:documents"))
    implementation(project(":feature:document-edit"))
    implementation(project(":feature:settings"))

    implementation(DependenciesPlugin.koinCore)
    implementation(DependenciesPlugin.koinAndroid)

    implementation(DependenciesPlugin.navigationRuntime)
    implementation(DependenciesPlugin.navigationCompose)
}