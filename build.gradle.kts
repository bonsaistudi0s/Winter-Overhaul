import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    `java`
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.11-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "8.3.8" apply false
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")

    version = "${rootProject.property("mod_version")}+${rootProject.property("minecraft_version")}"
    group = rootProject.property("maven_group") as String

    repositories {
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.parchmentmc.org")
        maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") {
            content {
                includeGroupByRegex("software\\.bernie.*")
                includeGroup("com.eliotlash.mclib")
            }
        }
    }
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "com.gradleup.shadow")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    loom.silentMojangMappingsLicense()
    loom.mixin {
        useLegacyMixinAp = false
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:${rootProject.property("minecraft_version")}")

        "mappings"(loom.layered() {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${rootProject.property("parchment_version")}:${rootProject.property("parchment_snapshot")}@zip")
        })
    }

    tasks.withType(JavaCompile::class.java) {
        options.encoding = "UTF-8"
        options.release.set(21)
    }
}
