import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

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
        "loader_version" to mod.prop("loader_version", ">=0.18.0"),
        "configmanagerVersion" to  mod.prop("configmanagerVersion")
    )
}

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

dependencies {
    fun highestMcVersionInRange(version: String): String {
        val parsed = version.split(".").mapNotNull { it.toIntOrNull() }
        val major = parsed.getOrNull(0) ?: return "unknown"
        val minor = parsed.getOrNull(1) ?: return "unknown"
        val patch = parsed.getOrNull(2) ?: 0

        return when {
            major == 1 && minor in 16..17 -> "1.17.1"
            major == 1 && minor == 18 -> "1.18.2"
            major == 1 && minor == 19 -> "1.20.4"
            major == 1 && minor == 20 && patch <= 4 -> "1.20.4"
            major == 1 && minor == 20 && patch == 5 -> "1.20.5"
            major == 1 && minor == 20 && patch == 6 -> "1.20.6"
            major == 1 && minor == 21 && patch <= 10 -> "1.21.10"
            major == 1 && minor == 21 && patch <= 11 -> "1.21.11"
            else -> "unsupported"
        }
    }

    when(mod.loader) {
        "fabric" -> {
            // The Config Manager
            modImplementation("maven.modrinth:config-manager:"
                    + mod.prop("configmanagerVersion")
                    + "+fabric-"
                    + highestMcVersionInRange(mod.minecraftVersion))
        }

        "neoforge" -> {
            // The Config Manager
            modImplementation("maven.modrinth:config-manager:"
                    + mod.prop("configmanagerVersion")
                    + "+neoforge-"
                    + highestMcVersionInRange(mod.minecraftVersion))
        }

        "forge" -> {
            // The Config Manager
            modImplementation("maven.modrinth:config-manager:"
                    + mod.prop("configmanagerVersion")
                    + "+forge-"
                    + highestMcVersionInRange(mod.minecraftVersion))
        }
    }
}
