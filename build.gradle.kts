plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.github.johnnyjayjay"
version = "1.0"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    implementation("com.github.johnnyjayjay:compatre:master-SNAPSHOT")
}

tasks {
    compileJava.get().options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}