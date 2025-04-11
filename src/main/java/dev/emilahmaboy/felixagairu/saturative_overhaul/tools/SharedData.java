package dev.emilahmaboy.felixagairu.saturative_overhaul.tools;

import com.google.gson.JsonObject;
import dev.felixagairu.configmanager.ConfigManager;

public class SharedData {


    private static final String defaultConfigs ="""
        {
        \t"foodLevel":400,
        \t"defaultFoodLevel":280,
        \t"nutritionModifier":5,
        \t"nutritionRandLow":0.8,
        \t"nutritionRandHigh":1.2,
        \t"saturationRandLow":0.8,
        \t"saturationRandHigh":1.2,
        \t"thresholdStd":300,
        \t"thresholdMin":25,
        \t"thresholdLow":100,
        \t"thresholdMid":250,
        \t"thresholdHigh":350
        }""";

    private static final ConfigManager cM = new ConfigManager("saturative_overhaul.json", defaultConfigs);
    private static final JsonObject configs = cM.loadConfig();

    public static int foodLevel = Integer.parseInt(configs.get("foodLevel").getAsString());
    public static final int defaultFoodLevel = Integer.parseInt(configs.get("defaultFoodLevel").getAsString());

    public static final float nutritionModifier = Float.parseFloat(configs.get("nutritionModifier").getAsString());
    public static final float nutritionRandLow = Float.parseFloat(configs.get("nutritionRandLow").getAsString());
    public static final float nutritionRandHigh = Float.parseFloat(configs.get("nutritionRandHigh").getAsString());
    public static final float saturationRandLow = Float.parseFloat(configs.get("saturationRandLow").getAsString());
    public static final float saturationRandHigh = Float.parseFloat(configs.get("saturationRandHigh").getAsString());

    public static final int thresholdStd = Integer.parseInt(configs.get("thresholdStd").getAsString());
    public static final int thresholdMin = Integer.parseInt(configs.get("thresholdMin").getAsString());
    public static final int thresholdLow = Integer.parseInt(configs.get("thresholdLow").getAsString());
    public static final int thresholdMid = Integer.parseInt(configs.get("thresholdMid").getAsString());
    public static final int thresholdHigh = Integer.parseInt(configs.get("thresholdHigh").getAsString());
}
