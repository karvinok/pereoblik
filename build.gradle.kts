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

tasks.register("buildPereoblikDependenciesPlugin") {
    finalizedBy(":build-logic:convention:build")
}.dependsOn("sync")