import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

group = "cc.dreamcode"
version = "1.0.1"
val mainPackage = "cc.dreamcode.guildpoints"

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()

    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")}
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://maven.enginehub.org/repo") }
}

dependencies {
    // Engine
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    // Cdn
    implementation("net.dzikoysk:cdn:1.14.1")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // FunnyGuilds
    compileOnly("net.dzikoysk.funnyguilds:plugin:4.10.2")
    compileOnly("eu.okaeri:okaeri-configs-yaml-bukkit:4.0.6")
    compileOnly("eu.okaeri:okaeri-configs-serdes-commons:4.0.6")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "$mainPackage.GuildPoints"
    apiVersion = "1.13"
    prefix = "dreamGuildPoints"
    author = "Piotrulla"
    name = "dreamGuildPoints"
    version = "${project.version}"
    depend = listOf("FunnyGuilds")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation")
    options.encoding = "UTF-8"
}

tasks {
    runServer {
        minecraftVersion("1.19.1")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("dreamGuildPoints v${project.version}.jar")

    exclude(
        "panda/std/**",
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**",
    )

    mergeServiceFiles()
    minimize()

    val prefix = "$mainPackage.libs"

    listOf(
        "org.bstats",
        "net.dzikoysk.cdn",
        "net.kyori",
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}