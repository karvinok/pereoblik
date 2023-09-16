plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("pereoblik.dependencies")
}

android {
    namespace = "com.vilinesoft.ui"
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
    implementation(DependenciesPlugin.androidLifecycle)
    implementation(DependenciesPlugin.androidLifecycleViewModel)
    api(DependenciesPlugin.androidLifecycleCompose)

    implementation(DependenciesPlugin.androidCore)

    api(DependenciesPlugin.immutableCollections)
    api(platform(DependenciesPlugin.composeBom))
    api(DependenciesPlugin.composeUi)
    api(DependenciesPlugin.composeUiGraphics)
    api(DependenciesPlugin.composeUiMaterial3)
    api(DependenciesPlugin.composeUiToolingPreview)
    debugApi(DependenciesPlugin.composeUiTooling)
}