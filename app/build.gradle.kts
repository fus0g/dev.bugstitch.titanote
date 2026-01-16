import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            implementation(libs.androidx.core.splashscreen)

            implementation(libs.hilt.android)

            implementation(libs.icons.lucide)

            implementation(libs.androidx.datastore.preferences)

            //koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "dev.bugstitch.titanote"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.bugstitch.titanote"
        minSdk = 25
        targetSdk = 35
        versionCode = 13
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

/* room {
    schemaDirectory("$projectDir/schemas")
} */

compose.desktop {
    application {
        mainClass = "dev.bugstitch.titanote.MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.AppImage,
                TargetFormat.Rpm,
                TargetFormat.Deb,
                TargetFormat.Pkg,
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Exe
            )
            packageName = "dev.bugstitch.titanote"
            packageVersion = "1.0.0"
        }
    }
}