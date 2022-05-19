val kotlin_version: String by project
val kotlinx_html_version: String by project
val ktor_version: String by project

plugins {
    kotlin("multiplatform") version "1.6.21"
    application
}

group = "com.rescribet.kotlin.reproductions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        compilations["main"].packageJson {
            name = "example"
            version = "1.0.0"
        }

        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
        }

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:$ktor_version")
                implementation("io.ktor:ktor-html-builder:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinx_html_version")
            }
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

application {
    mainClass.set("com.rescribet.kotlin.reproductions.application.ServerKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}
