import ext.implementation

plugins {
    id(ModulePlugin.MODULE_NAME)
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.raven.home"

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    di()
    general()
    testing()
    network()
    navigation()
    database()

    testImplementation("io.vertx:vertx-codegen:3.6.0")
    implementation(project(":core"))
    implementation(project(":common:database"))}
