package dev.emilahmaboy.felixagairu.saturative_overhaul.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ConfigManager {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "saturative_overhaul.json");
    private static final Gson GSON = new Gson();

    public static JsonObject loadConfig() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            return GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            return ConfigManager.saveConfig(JsonParser.parseString(
                    "{\n" +
                            "\t\"foodLevel\":400,\n" +
                            "\t\"defaultFoodLevel\":280,\n" +
                            "\t\"nutritionModifier\":5,\n" +
                            "\t\"nutritionRandLow\":0.8,\n" +
                            "\t\"nutritionRandHigh\":1.2,\n" +
                            "\t\"saturationRandLow\":0.8,\n" +
                            "\t\"saturationRandHigh\":1.2,\n" +
                            "\t\"thresholdStd\":300,\n" +
                            "\t\"thresholdMin\":25,\n" +
                            "\t\"thresholdLow\":100,\n" +
                            "\t\"thresholdMid\":250,\n" +
                            "\t\"thresholdHigh\":350\n" +
                            "}"
            ).getAsJsonObject()); // Return default config
        }
    }

    public static JsonObject saveConfig(JsonObject config) {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
            return config;
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
            return new JsonObject();
        }
    }
    /*
    public static void registerShutdownHandler() {
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            // Save your configuration file here
            saveConfig();
        });
    }
 */
}
