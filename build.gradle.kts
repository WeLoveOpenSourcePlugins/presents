plugins {
    java
}

group = "com.github.johnnyjayjay"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.15.2-R0.1-SNAPSHOT")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}