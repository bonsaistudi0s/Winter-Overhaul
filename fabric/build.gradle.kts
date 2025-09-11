plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

base {
    archivesName = "${rootProject.property("mod_name")}-Fabric"
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
    get("developmentFabric").extendsFrom(common)
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric_api_version")}")

    modImplementation("software.bernie.geckolib:geckolib-fabric-${rootProject.property("minecraft_version")}:${rootProject.property("geckolib_version")}")
    modImplementation("dev.architectury:architectury-fabric:${rootProject.property("architectury_version")}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

tasks {
    processResources {
        properties(listOf("fabric.mod.json"),
            "mod_version" to rootProject.property("mod_version"),
            "minecraft_version" to rootProject.property("minecraft_version"),
            "geckolib_version" to rootProject.property("geckolib_version"),

            "fabric_loader_version" to rootProject.property("fabric_loader_version"),
            "fabric_api_version" to rootProject.property("fabric_api_version"),
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
