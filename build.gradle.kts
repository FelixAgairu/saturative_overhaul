import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

// configure the maven publication
publishMods {
    dryRun = false
    changelog = "See GitHub for changelog"

    modrinth {
        if (mod.isFabric) {
            requires("fabric-api")
        }
        requires("config-manager")
    }
}

modSettings {
    variableReplacements = mapOf(
        "loader_version" to mod.prop("loader_version", ">=0.17.3")
    )
}

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
//    maven("https://maven.ryanliptak.com")
}

dependencies {
    val configmanagerVersion: String by project

    // Apple Skin
//    modImplementation("squeek.appleskin:appleskin-${mod.loader}:mc${mod.prop("${mod.loader}-appleskin_version")}")
//    include("squeek.appleskin:appleskin-${mod.loader}:mc${mod.prop("${mod.loader}-appleskin_version")}")

    // The Config Manager
    modImplementation("maven.modrinth:config-manager:$configmanagerVersion+${mod.loader}-"+lowestMcVersionInRange(
        mod.prop(
            "minecraft_version"
        )
    ))
    include("maven.modrinth:config-manager:$configmanagerVersion+${mod.loader}-"+lowestMcVersionInRange(
        mod.prop(
            "minecraft_version"
        )
    ))
}

fun lowestMcVersionInRange(version: String): String {
    val parsed = version.split(".").mapNotNull { it.toIntOrNull() }
    val major = parsed.getOrNull(0) ?: return "unknown"
    val minor = parsed.getOrNull(1) ?: return "unknown"
    val patch = parsed.getOrNull(2) ?: 0

    return when {
        major == 1 && minor in 16..17 -> "1.16.5"
        major == 1 && minor == 18 -> "1.18"
        major == 1 && minor == 19 -> "1.19"
        major == 1 && minor == 20 && patch <= 5 -> "1.19"
        major == 1 && minor == 20 -> "1.20.6"
        major == 1 && minor == 21 -> "1.20.6"
        else -> "unsupported"
    }
}
