import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin : Plugin<Project> {
    override fun apply(target: Project) {}
    companion object {
        const val compileSdkVersion = 34
        const val minSdkVersion = 24
        const val targetSdkVersion = 34
        const val versionCodeVersion = 1
        const val versionNameVersion = "0.0.1"
        const val composeCompilerExtensionVersion = "1.5.1"
        val javaVersion = JavaVersion.VERSION_17

        private const val koinVersion = "3.4.3"
        private const val composeBomVersion = "2023.08.00"
        private const val tracingVersion = "1.1.0"
        private const val navigationVersion = "2.7.0"
        private const val lifecycleVersion = "2.6.1"
        private const val roomVersion = "2.5.2"
        private const val retrofitVersion = "2.9.0"
        private const val interceptorVersion = "4.9.1"

        const val androidCore = "androidx.core:core-ktx:1.10.1"
        const val androidLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}"
        const val androidLifecycleCompose = "androidx.lifecycle:lifecycle-runtime-compose:${lifecycleVersion}"
        const val androidLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}"

        const val navigationCommon = "androidx.navigation:navigation-common-ktx:${navigationVersion}"
        const val navigationCommon2 = "androidx.navigation:navigation-common-ktx:${navigationVersion}"
        const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${navigationVersion}"
        const val navigationCompose = "androidx.navigation:navigation-compose:${navigationVersion}"

        const val koinCore = "io.insert-koin:koin-core:${koinVersion}"
        const val koinAndroid = "io.insert-koin:koin-android:${koinVersion}"
        const val koinComposeNavigation = "io.insert-koin:koin-androidx-compose-navigation:${koinVersion}"

        const val activityCompose = "androidx.activity:activity-compose:1.7.2"
        const val composeBom = "androidx.compose:compose-bom:$composeBomVersion"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
        const val composeUiMaterial3 = "androidx.compose.material3:material3"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling"

        //Data
        const val roomKsp = "androidx.room:room-compiler:$roomVersion"
        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$interceptorVersion"
        const val timber = "com.jakewharton.timber:timber:4.7.1"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0-RC"

        const val tracing = "androidx.tracing:tracing-ktx:${tracingVersion}"
        const val test = "io.insert-koin:koin-test:${koinVersion}"
//
//        testImplementation("junit:junit:4.13.2")
//        androidTestImplementation("androidx.test.ext:junit:1.1.5")
//        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
//        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//        debugImplementation("androidx.compose.ui:ui-tooling")
//        debugImplementation("androidx.compose.ui:ui-test-manifest")
    }

}