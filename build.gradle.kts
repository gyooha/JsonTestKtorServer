import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.Coroutines

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.github.jengelman.gradle.plugins:shadow:2.0.1")
    }
}

plugins {
    application
    kotlin("jvm") version Versions.kotlin
}

apply(plugin = "com.github.johnrengelman.shadow")

application {
    group = "seroo"
    version = "0.0.1"
    mainClassName = "io.ktor.server.netty.EngineMain"
}

sourceSets {
    getByName("main") {
        java.srcDirs("src")
        resources.srcDir("resources")
    }

    getByName("test") {
        java.srcDirs("test")
        resources.srcDirs("testresources")
    }
}

repositories {
    mavenLocal()
    jcenter()
    "https://kotlin.bintray.com".also {
        maven { setUrl("$it/ktor") }
        maven { setUrl("$it/kotlin-js-wrappers") }
    }

    maven { setUrl("https://plugins.gradle.org/m2/") }
}

kotlin.experimental.coroutines = Coroutines.ENABLE

dependencies {
    kotlinGroup()
    serverGroup()
    logGroup()
    clientGroup()
    jsonGroup()
    testGroup()
}

tasks.withType<ShadowJar> {
    baseName = ServerInfo.baseName
    version = ""
    classifier = ""
}

tasks.create("docker_runner") {

}