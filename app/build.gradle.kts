import config.ProjectConfig

plugins {
    id(AppPlugin.PLUGIN_APP)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = ProjectConfig.appId
}

dependencies {
    di()
    general()
    testing()
    navigation()

    implementation(project(":core"))
    implementation(project(":feature:home"))
    implementation(project(":common:network"))
}
