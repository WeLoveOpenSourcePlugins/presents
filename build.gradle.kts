plugins {
    java
    //id("com.github.johnrengelman.shadow") version "6.0.0"
    id("me.bristermitten.pdm") version "0.0.11"
}

group = "com.github.johnnyjayjay"
version = "0.2.0"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    pdm("com.github.johnnyjayjay:compatre:v0.2.2-alpha")
}

tasks {
    compileJava.get().options.encoding = "UTF-8"

    jar {
        dependsOn("pdm")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}