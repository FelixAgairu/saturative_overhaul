package dev.emilahmaboy.felixagairu.saturative_overhaul.tools;

import com.google.gson.JsonObject;

public class SharedData {
    private static final JsonObject configs = ConfigManager.loadConfig();

    public static final int foodLevel = Integer.parseInt(configs.get("foodLevel").getAsString());
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
