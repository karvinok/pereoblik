plugins {
    `kotlin-dsl`
}

group = "com.vilinesoft.build-logic"

gradlePlugin {
    plugins {
        register("dependenciesPlugin") {
            id = "pereoblik.dependencies"
            implementationClass = "DependenciesPlugin"
        }
    }
}
