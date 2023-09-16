import com.android.build.gradle.internal.tasks.factory.dependsOn

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    kotlin("android") version "1.9.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//This task is made to rebuild "dependencies plugin" with gradle sync
// to receive all the deps without errors
tasks.register("buildPereoblikDependenciesPlugin") {
    finalizedBy(":build-logic:convention:build")
}.dependsOn("sync")

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
        }
    }
}