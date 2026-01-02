/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

import com.google.gson.JsonObject;
import dev.felixagairu.configmanager.ConfigManager;

import static dev.felixagairu.configmanager.utils.Parsers.*;
import static dev.felixagairu.saturative_overhaul.Saturative.MOD_ID;

public class ConfigData {
    private static final String defaultConfigs = """
    {
        "maxFoodLevel":400,
        "defaultFoodLevel":280,
        
        "exhaustionModifier":3.5,
        "exhaustionCap":60.0,
        "nutritionModifier":5.0,
        "nutritionRandLow":0.8,
        "nutritionRandHigh":1.2,
        "saturationRandLow":0.8,
        "saturationRandHigh":1.2,
        
        "thresholdStd":300,
        "threshold_Min_ShortEffectId":"minecraft:slowness",
        "threshold_Min_ShortEffectAmplification":1,
        "threshold_Min_LongEffectId":"minecraft:nausea",
        "threshold_Min_LongEffectAmplification":0,
        "thresholdMin":25,
        "thresholdMin_Low_EffectId":"minecraft:slowness",
        "thresholdMin_Low_EffectAmplification":0,
        "thresholdLow":100,
        "thresholdMid":250,
        "thresholdHigh":350,
        "thresholdHigh_ShortEffectId":"minecraft:slowness",
        "thresholdHigh_ShortEffectAmplification":0,
        "thresholdHigh_LongEffectId":"minecraft:nausea",
        "thresholdHigh_LongEffectAmplification":0,
        "thresholdHigh_Max_ShortEffectId":"minecraft:slowness",
        "thresholdHigh_Max_ShortAmplification":1,
        "thresholdHigh_Max_LongEffectId":"minecraft:nausea",
        "thresholdHigh_Max_LongEffectAmplification":0,
        
        "decreaseFoodLevelOverTimeEnabled":true,
        "randomDecreaseFoodLevelEnabled":true,
        "randomDecreaseFoodLevelBaseTicks":80,
        "randomDecreaseFoodLevelMinMultiplier":0.5,
        "randomDecreaseFoodLevelMaxMultiplier":1.5,
        "randomDecreaseFoodLevelPerTickAmounts":1,
        "randomAddExhaustionPerTickAmounts":0.025,
        
        "damageEffectsStarving":1.0,
        "damageEffectsOvereating":1.0,
        
        "addRealDamageEnabled":false,
        "damagePreTickStarving":0.025,
        "damagePreTickOvereating":0.025,
        "damagePreTickEffectsStarving":0.001,
        "damagePreTickEffectsOvereating":0.001
    }
    """;
    static {
        ConfigManager.setDefault(MOD_ID, defaultConfigs);
        ConfigManager.setConfigsFile(MOD_ID);
    }
    private static final JsonObject configs = ConfigManager.getConfigs(MOD_ID);

    public static final int maxFoodLevel = (int) clampValue(parseIntegerSafe(configs.get("maxFoodLevel")));
    public static final int defaultFoodLevel = (int) clampValue(parseIntegerSafe(configs.get("defaultFoodLevel")));

    public static final float exhaustionModifier = (float) clampValue(parseFloatSafe(configs.get("exhaustionModifier")));
    public static final float exhaustionCap = (float) clampValue(parseFloatSafe(configs.get("exhaustionCap")));
    public static final float nutritionModifier = (float) clampValue(parseFloatSafe(configs.get("nutritionModifier")));
    public static final float nutritionRandLow = (float) clampValue(parseFloatSafe(configs.get("nutritionRandLow")));
    public static final float nutritionRandHigh = (float) clampValue(parseFloatSafe(configs.get("nutritionRandHigh")));
    public static final float saturationRandLow = (float) clampValue(parseFloatSafe(configs.get("saturationRandLow")));
    public static final float saturationRandHigh = (float) clampValue(parseFloatSafe(configs.get("saturationRandHigh")));

    public static final int thresholdStd = (int) clampValue(parseIntegerSafe(configs.get("thresholdStd")));
    public static final int thresholdMin = (int) clampValue(parseIntegerSafe(configs.get("thresholdMin")));
    public static final int thresholdLow = (int) clampValue(parseIntegerSafe(configs.get("thresholdLow")));
    public static final int thresholdMid = (int) clampValue(parseIntegerSafe(configs.get("thresholdMid")));
    public static final int thresholdHigh = (int) clampValue(parseIntegerSafe(configs.get("thresholdHigh")));

    public static final String threshold_Min_ShortEffectId = parseStringSafe(configs.get("threshold_Min_ShortEffectId"));
    public static final String threshold_Min_LongEffectId = parseStringSafe(configs.get("threshold_Min_LongEffectId"));
    public static final String thresholdMin_Low_EffectId = parseStringSafe(configs.get("thresholdMin_Low_EffectId"));
    public static final String thresholdHigh_ShortEffectId = parseStringSafe(configs.get("thresholdHigh_ShortEffectId"));
    public static final String thresholdHigh_LongEffectId = parseStringSafe(configs.get("thresholdHigh_LongEffectId"));
    public static final String thresholdHigh_Max_ShortEffectId = parseStringSafe(configs.get("thresholdHigh_Max_ShortEffectId"));
    public static final String thresholdHigh_Max_LongEffectId = parseStringSafe(configs.get("thresholdHigh_Max_LongEffectId"));

    public static final int threshold_Min_ShortEffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("threshold_Min_ShortEffectAmplification")));
    public static final int threshold_Min_LongEffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("threshold_Min_LongEffectAmplification")));
    public static final int thresholdMin_Low_EffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("thresholdMin_Low_EffectAmplification")));
    public static final int thresholdHigh_ShortEffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("thresholdHigh_ShortEffectAmplification")));
    public static final int thresholdHigh_LongEffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("thresholdHigh_LongEffectAmplification")));
    public static final int thresholdHigh_Max_ShortAmplification = (int) clampValue(parseIntegerSafe(configs.get("thresholdHigh_Max_ShortAmplification")));
    public static final int thresholdHigh_Max_LongEffectAmplification = (int) clampValue(parseIntegerSafe(configs.get("thresholdHigh_Max_LongEffectAmplification")));

    public static final boolean decreaseFoodLevelOverTimeEnabled = parseBooleanSafe(configs.get("decreaseFoodLevelOverTimeEnabled"));
    public static final boolean randomDecreaseFoodLevelEnabled = parseBooleanSafe(configs.get("randomDecreaseFoodLevelEnabled"));
    public static final int randomDecreaseFoodLevelBaseTicks = (int) clampValue(parseIntegerSafe(configs.get("randomDecreaseFoodLevelBaseTicks")));
    public static final float randomDecreaseFoodLevelMinMultiplier = (float) clampValue(parseFloatSafe(configs.get("randomDecreaseFoodLevelMinMultiplier")));
    public static final float randomDecreaseFoodLevelMaxMultiplier = (float) clampValue(parseFloatSafe(configs.get("randomDecreaseFoodLevelMaxMultiplier")));
    public static final int randomDecreaseFoodLevelPerTickAmounts = (int) clampValue(parseIntegerSafe(configs.get("randomDecreaseFoodLevelPerTickAmounts")));
    public static final float randomAddExhaustionPerTickAmounts = (float) clampValue(parseFloatSafe(configs.get("randomAddExhaustionPerTickAmounts")));

    public static final float damageEffectsStarving = (float) clampValue(parseFloatSafe(configs.get("damageEffectsStarving")));
    public static final float damageEffectsOvereating = (float) clampValue(parseFloatSafe(configs.get("damageEffectsOvereating")));

    public static final boolean addRealDamageEnabled = parseBooleanSafe(configs.get("addRealDamageEnabled"));
    public static final float damagePreTickStarving = (float) clampValue(parseFloatSafe(configs.get("damagePreTickStarving")));
    public static final float damagePreTickOvereating = (float) clampValue(parseFloatSafe(configs.get("damagePreTickOvereating")));
    public static final float damagePreTickEffectsStarving = (float) clampValue(parseFloatSafe(configs.get("damagePreTickEffectsStarving")));
    public static final float damagePreTickEffectsOvereating = (float) clampValue(parseFloatSafe(configs.get("damagePreTickEffectsOvereating")));

    private ConfigData() {}

    public static Object clampValue(Object value) {
        long min = 0;
        long max = 65535;

        if (value instanceof Integer i) {
            return Math.clamp((long) i, (int) min, (int) max);
        }
        if (value instanceof Float f) {
            float fMin = (float) min;
            float fMax = (float) max;
            return Math.clamp(f, fMin, fMax);
        }

        // unsupported type → return unchanged
        return value;
    }

}
