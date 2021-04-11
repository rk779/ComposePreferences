buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        // kotlinx-collections-immutable-jvm used by Compose is stored here.
        maven("https://dl.bintray.com/kotlin/kotlinx") {
            name = "KotlinX Bintray"
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions.freeCompilerArgs += listOf( "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}