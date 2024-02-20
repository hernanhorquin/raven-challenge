plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.common.database"
}

dependencies {
    di()
    general()
    database()
}