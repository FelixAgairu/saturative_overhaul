/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.tools;

import com.google.gson.JsonObject;
import dev.felixagairu.configmanager.ConfigManager;

import static dev.felixagairu.saturative_overhaul.Saturative.MODID;

public class ConfigData {
    private static final String defaultConfigs ="""
    {
        "foodLevelMax":400,
        "defaultFoodLevel":280,
        "exhaustionModifier":3.5,
        "exhaustionCap":60,
        "nutritionModifier":5,
        "nutritionRandLow":0.8,
        "nutritionRandHigh":1.2,
        "saturationRandLow":0.8,
        "saturationRandHigh":1.2,
        "thresholdStd":300,
        "thresholdMin":25,
        "thresholdLow":100,
        "thresholdMid":250,
        "thresholdHigh":350
    }
    """;

    private static final ConfigManager cM = new ConfigManager(MODID + ".json", defaultConfigs);
    private static final JsonObject configs = cM.loadConfig();

    public static final int foodLevelMax = ConfigManager.parseIntegerSafe(configs.get("foodLevelMax"));
    public static final int defaultFoodLevel = ConfigManager.parseIntegerSafe(configs.get("defaultFoodLevel"));

    public static final float exhaustionModifier = ConfigManager.parseFloatSafe(configs.get("exhaustionModifier"));
    public static final float exhaustionCap = ConfigManager.parseFloatSafe(configs.get("exhaustionCap"));
    public static final float nutritionModifier = ConfigManager.parseFloatSafe(configs.get("nutritionModifier"));
    public static final float nutritionRandLow = ConfigManager.parseFloatSafe(configs.get("nutritionRandLow"));
    public static final float nutritionRandHigh = ConfigManager.parseFloatSafe(configs.get("nutritionRandHigh"));
    public static final float saturationRandLow = ConfigManager.parseFloatSafe(configs.get("saturationRandLow"));
    public static final float saturationRandHigh = ConfigManager.parseFloatSafe(configs.get("saturationRandHigh"));

    public static final int thresholdStd = ConfigManager.parseIntegerSafe(configs.get("thresholdStd"));
    public static final int thresholdMin = ConfigManager.parseIntegerSafe(configs.get("thresholdMin"));
    public static final int thresholdLow = ConfigManager.parseIntegerSafe(configs.get("thresholdLow"));
    public static final int thresholdMid = ConfigManager.parseIntegerSafe(configs.get("thresholdMid"));
    public static final int thresholdHigh = ConfigManager.parseIntegerSafe(configs.get("thresholdHigh"));
}
