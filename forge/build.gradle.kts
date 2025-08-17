plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

val generatedResources = file("src/generated")

sourceSets {
    main {
        resources.srcDir(generatedResources)
    }
}

loom {
    runs {
        create("data") {
            data()

            programArgs("--all", "--mod", rootProject.property("mod_id") as String)
            programArgs("--output", generatedResources.absolutePath)
        }
    }

    forge {
        mixinConfig("winteroverhaul.mixins.json")
    }
}

base {
    archivesName = "${rootProject.property("mod_name")}-Forge"
}

val common: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shadowCommon: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    get("developmentForge").extendsFrom(common)
}

dependencies {
    forge("net.minecraftforge:forge:${rootProject.property("minecraft_version")}-${rootProject.property("forge_version")}")

    modImplementation("software.bernie.geckolib:geckolib-forge-${rootProject.property("minecraft_version")}:${rootProject.property("geckolib_version")}")
    modImplementation("dev.architectury:architectury-forge:${rootProject.property("architectury_version")}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }

    // Forge doesn't bundle MixinExtras, so we should include it.
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")!!)
    implementation(include("io.github.llamalad7:mixinextras-forge:0.5.0")!!)
}

tasks {
    processResources {
        properties(listOf("META-INF/mods.toml"),
            "mod_version" to rootProject.property("mod_version"),
            "minecraft_version_forge" to rootProject.property("minecraft_version_forge"),
            "geckolib_version" to rootProject.property("geckolib_version"),
            "forge_version" to rootProject.property("forge_version")
        )
    }

    shadowJar {
        exclude("architectury.common.json")

        configurations = listOf(shadowCommon)
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }

    jar {
        archiveClassifier.set("dev")
    }
}
