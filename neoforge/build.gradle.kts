plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
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
}

base {
    archivesName = "${rootProject.property("mod_name")}-NeoForge"
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
    get("developmentNeoForge").extendsFrom(common)
}

dependencies {
    neoForge("net.neoforged:neoforge:${rootProject.property("neoforge_version")}")

    modImplementation("software.bernie.geckolib:geckolib-neoforge-${rootProject.property("minecraft_version")}:${rootProject.property("geckolib_version")}")
    modImplementation("dev.architectury:architectury-neoforge:${rootProject.property("architectury_version")}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionNeoForge")) { isTransitive = false }
}

tasks {
    processResources {
        properties(listOf("META-INF/neoforge.mods.toml"),
            "mod_version" to rootProject.property("mod_version"),
            "minecraft_version_forge" to rootProject.property("minecraft_version_forge"),
            "geckolib_version" to rootProject.property("geckolib_version"),
            "neoforge_version" to rootProject.property("neoforge_version"),
            "architectury_version" to rootProject.property("architectury_version")
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
