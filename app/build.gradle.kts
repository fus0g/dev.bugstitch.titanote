import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val version:String = "1.3.0"
val versionCode = 15

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    jvm()

    sourceSets {

        val jvmMain by getting {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
        }

        val commonMain by getting {
            kotlin.srcDir("build/generated/commonMain")
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.core.splashscreen)


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
            api(libs.koin.annotation)

            //navigation
            implementation(libs.navigation.compose)
            implementation(libs.compose.backhandler)

            implementation(libs.icons.lucide)

            //room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.kotlinx.datetime)

            implementation(libs.compose.rich.editor)

            implementation(libs.kotlinx.serialization.json)

    }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }

        sourceSets.named("commonMain").configure {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }

    }
}

val generateCommonBuildConfig by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/commonMain")

    outputs.dir(outputDir)

    doLast {
        val file = outputDir.get().file("BuildConfig.kt").asFile
        file.parentFile.mkdirs()

        file.writeText(
            """
            package dev.bugstitch.titanote

            object CommonBuildConfig {
                const val VERSION_NAME = "$version"
                const val VERSION_CODE = $versionCode
            }
            """.trimIndent()
        )
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn(generateCommonBuildConfig)
}

tasks.withType<com.google.devtools.ksp.gradle.KspAATask>().configureEach {
    dependsOn(generateCommonBuildConfig)
}

android {
    namespace = "dev.bugstitch.titanote"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.bugstitch.titanote"
        minSdk = 25
        targetSdk = 35
        versionCode = 15
        versionName = "1.3.0"

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
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspJvm", libs.koin.ksp.compiler)

    add("kspCommonMainMetadata", libs.androidx.room.compiler)
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspJvm", libs.androidx.room.compiler)
}

 room {
    schemaDirectory("$projectDir/schemas")
}

compose.desktop {
    application {
        mainClass = "dev.bugstitch.titanote.MainKt"

        buildTypes.release {
            proguard {

                configurationFiles.from(project.file("proguard-rules-desktop.pro"))
            }
        }
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
            packageVersion = version
        }
    }
}
