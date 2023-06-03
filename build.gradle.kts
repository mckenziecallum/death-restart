plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("io.freefair.lombok") version "8.0.1"
}

group = "com.cjmckenzie"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}