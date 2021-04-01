plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta03"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            val release by publications.registering(MavenPublication::class) {
                from(components["release"])
                artifact(sourcesJar.get())
                artifactId = "preferences-ui"
                groupId = "com.github.rk779.ComposePreferences"
                version = "1.0.0"
            }
        }
    }
}

dependencies {
    // Manager
    api(project(":datastore:manager"))
    // Compose
    implementation(AndroidX.compose.material)
    implementation("com.google.accompanist", "accompanist-insets", "_")
}