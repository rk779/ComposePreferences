import de.fayard.refreshVersions.bootstrapRefreshVersions

include(":datastore:compose")
include(":datastore:manager")
include(":app")
rootProject.name = "ComposePreferences"

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

bootstrapRefreshVersions()