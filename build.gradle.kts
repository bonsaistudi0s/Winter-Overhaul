import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.ReleaseType
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask

plugins {
    `java`
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.10-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "8.3.8" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.7.+" apply false
}

val modVersion = createVersion()

fun createVersion(): String {
    // Override to use the GitHub tag from Releases
    if (System.getenv("GITHUB_TAG") != null) {
        return System.getenv("GITHUB_TAG").removePrefix("v")
    }

    return rootProject.property("mod_version") as String
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")

    version = "${modVersion}+${rootProject.property("minecraft_version")}"
    group = rootProject.property("maven_group") as String

    repositories {
        maven("https://maven.architectury.dev/")
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
        options.release.set(17)
    }

    if (project.path.contains("fabric") || project.path.contains("forge")) {
        apply(plugin = "me.modmuss50.mod-publish-plugin")

        val properLoaderName = when (project.property("loom.platform")) {
            "fabric" -> "Fabric"
            "forge" -> "Forge"
            "neoforge" -> "NeoForge"
            else -> ""
        }

        project.extensions.configure<ModPublishExtension>("publishMods") {
            val mcVersion = rootProject.property("minecraft_version") as String

            file = tasks.named<RemapJarTask>("remapJar").get().archiveFile
            displayName = "[$properLoaderName] Winter Overhaul - $modVersion - $mcVersion"
            version = "$modVersion+${mcVersion}-${project.property("loom.platform")}"
            changelog = System.getenv("RELEASE_DESCRIPTION") ?: ""
            type = ReleaseType.STABLE
            modLoaders.add(project.property("loom.platform").toString())

            dryRun = providers.environmentVariable("MODRINTH_TOKEN")
                .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

            modrinth {
                projectId = rootProject.property("publishing.modrinth").toString()
                accessToken = providers.environmentVariable("MODRINTH_TOKEN")
                minecraftVersions.addAll(rootProject.property("supported_versions").toString().split(","))
                if (project.property("loom.platform") == "fabric") {
                    requires {
                        slug = "fabric-api"
                    }
                }
                requires {
                    slug = "architectury-api"
                }
                requires {
                    slug = "geckolib"
                }
            }

            curseforge {
                projectId = rootProject.property("publishing.curseforge").toString()
                accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
                minecraftVersions.addAll(rootProject.property("supported_versions").toString().split(","))
                if (project.property("loom.platform") == "fabric") {
                    requires {
                        slug = "fabric-api"
                    }
                }
                requires {
                    slug = "architectury-api"
                }
                requires {
                    slug = "geckolib"
                }
            }
        }
    }
}
