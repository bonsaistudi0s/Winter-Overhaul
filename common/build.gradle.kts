architectury {
    common("fabric", "neoforge")
}

base {
    archivesName = "${rootProject.property("mod_name")}-Common"
}

dependencies {
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")

    modImplementation("software.bernie.geckolib:geckolib-common-${rootProject.property("minecraft_version")}:${rootProject.property("geckolib_version")}")

    modImplementation("dev.architectury:architectury:${rootProject.property("architectury_version")}")
}