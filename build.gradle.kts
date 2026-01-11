import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

fun lowestMcVersionInRange(version: String): String {
    val parsed = version.split(".").mapNotNull { it.toIntOrNull() }
    val major = parsed.getOrNull(0) ?: return "unknown"
    val minor = parsed.getOrNull(1) ?: return "unknown"
    val patch = parsed.getOrNull(2) ?: 0

    return when {
        major == 1 && minor == 16 && patch == 5 -> "1.16.5"
        major == 1 && minor == 17 -> "1.16.5"
        major == 1 && minor == 18 -> "1.18"
        major == 1 && minor == 19 -> "1.19"
        major == 1 && minor == 20 && patch <= 4 -> "1.19"
        major == 1 && minor == 20 && patch == 5 -> "1.20.5"
        major == 1 && minor == 20 && patch == 6 -> "1.20.6"
        major == 1 && minor == 21 && patch <= 10 -> "1.21.1"
        major == 1 && minor == 21 && patch <= 11 -> "1.21.11"
        else -> "unsupported"
    }
}

val modConfigManagerVersion = mod.prop("configmanagerVersion") + "+" + mod.loader + "-" + lowestMcVersionInRange(mod.minecraftVersion)

// configure the maven publication
publishMods {
    dryRun = false
    changelog = file("../../changelogs/${project.property("mod.version")}.md").readText()

    modrinth {
        if (mod.isFabric) {
            requires("fabric-api")
        }
        requires("config-manager")
    }
}

modSettings {
    variableReplacements = mapOf(
        "license" to mod.prop("mod.license"),
        "fabricLoaderVersion" to mod.prop("loader_version"),
        "configmanagerVersion" to  mod.prop("configmanagerVersion"),
        "modConfigManagerVersion" to modConfigManagerVersion,
        "minecraftVersionRange" to mod.prop("additional_versions").split(",").joinToString(prefix = "[", postfix = "]") { "\"$it\"" }
    )
}

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

dependencies {
    modImplementation("maven.modrinth:config-manager:$modConfigManagerVersion")
//    when(mod.loader) {
//        "fabric" -> {
//            // The Config Manager
//            modImplementation("maven.modrinth:config-manager:"
//                    + mod.prop("configmanagerVersion")
//                    + "+fabric-"
//                    + highestMcVersion
//            )
//        }
//
//        "neoforge" -> {
//            // The Config Manager
//            modImplementation("maven.modrinth:config-manager:"
//                    + mod.prop("configmanagerVersion")
//                    + "+neoforge-"
//                    + highestMcVersion
//            )
//        }
//
//        "forge" -> {
//            // The Config Manager
//            modImplementation("maven.modrinth:config-manager:"
//                    + mod.prop("configmanagerVersion")
//                    + "+forge-"
//                    + highestMcVersion
//            )
//        }
//    }
}
