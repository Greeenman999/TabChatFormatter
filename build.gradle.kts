import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "de.greenman999"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://nexus.sparky.ac/repository/Sparky/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
    implementation("dev.jorel.CommandAPI:commandapi-shade:6.5.3")
    annotationProcessor("dev.jorel.CommandAPI:commandapi-annotations:6.5.3")
    compileOnly("net.luckperms:api:5.3")
    implementation("net.kyori:adventure-platform-bukkit:4.0.0")
    implementation("net.kyori:adventure-api:4.9.3")
    implementation("net.kyori:adventure-text-minimessage:4.10.0-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.1")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    runServer {
        minecraftVersion("1.18.1")
    }
    val copyServerJar = task<Copy>("copyServerJar") {
        from(shadowJar)
        into("Server/plugins")
    }
    shadowJar {
        finalizedBy(copyServerJar)
    }
}


