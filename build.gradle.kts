/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */
import org.gradle.jvm.tasks.Jar

ext["spek_version"] = "2.0.0-rc.1"
ext["junit_version"] = "5.2.0"
ext["kotlin_version"] = "1.3.11"
ext["mockito_kotlin_version"] = "2.0.0"
ext["mockito_version"] = "2.23.4"
ext["expect_version"] = "1.0.1"

plugins {

    // Apply the application to add support for building a CLI application
    application
    jacoco  // Test coverage https://docs.gradle.org/current/userguide/jacoco_plugin.html
    findbugs  // Static Analyzer
    pmd       // Static Analyzer

    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
    id("org.jetbrains.kotlin.jvm").version("1.3.11")

    // Documentation
    id ("org.jetbrains.dokka").version("0.9.17")

}

application {
    // Define the main class for the application
    mainClassName = "basic.demo.AppKt"

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// setup the test task
val test by tasks.getting(Test::class) {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

dependencies {

    // Use the Kotlin JDK 8 standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${ext["kotlin_version"]}")

    // Use the Kotlin test library
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // JUnit 5
    testRuntime("org.junit.jupiter:junit-jupiter-engine:${ext["junit_version"]}")

    // Spek2
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:${ext["spek_version"]}") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:${ext["spek_version"]}") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:${ext["kotlin_version"]}")

    // Mockito
    testImplementation("org.mockito:mockito-core:${ext["mockito_version"]}")

    // Mockito-Kotlin
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${ext["mockito_kotlin_version"]}")

    // expect.kt
    testImplementation("com.nhaarman:expect.kt:${ext["expect_version"]}")

}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

findbugs {
    isIgnoreFailures = true
}

pmd {
    isIgnoreFailures = true
}

jacoco {
  toolVersion = "0.8.2"
}

tasks {

  // Test coverage reporting
  jacocoTestReport {
      // Enable xml for coveralls.
      reports {
          html.isEnabled = true
          xml.isEnabled = true
          csv.isEnabled = false
          xml.destination = file("${buildDir}/reports/jacoco/test/jacocoTestReport.xml")
          html.destination = file("${buildDir}/reports/jacoco/html/jacocoTestReport.html")
      }
  }

  dokka {
    outputFormat = "html"
    outputDirectory = "${buildDir}/javadoc"
  }
}

val dokkaJar by tasks.creating(Jar::class) {
  group = JavaBasePlugin.DOCUMENTATION_GROUP
  description = "Assembles Kotlin docs with Dokka"
  classifier = "javadoc"
  from(tasks.dokka)
}
