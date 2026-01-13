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

import static dev.felixagairu.configmanager.utils.Getter.*;
import static dev.felixagairu.configmanager.utils.Parsers.*;
import static dev.felixagairu.saturative_overhaul.Saturative.MOD_ID;
import static dev.felixagairu.saturative_overhaul.util.ConfigDefault.defaultConfigs;

public class ConfigHelper {
    static {
        ConfigManager.setDefault(MOD_ID, ConfigTranslator.transformJsonString(defaultConfigs));
        ConfigManager.setConfigsFile(MOD_ID);
    }
    private static final JsonObject configs = ConfigManager.getConfigs(MOD_ID);

	public static final int defaultFoodLevel = (int) clampValue(parseIntegerSafe(deepGet(configs, "defaultFoodLevel")));
	public static final int guiStarving = (int) clampValue(parseIntegerSafe(deepGet(configs, "guiStarving")));
	public static final int guiNormal = (int) clampValue(parseIntegerSafe(deepGet(configs, "guiNormal")));
	public static final int guiOvereating = (int) clampValue(parseIntegerSafe(deepGet(configs, "guiOvereating")));
	//threshold
	public static final int thresholdMin = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdMin")));
	public static final int thresholdLow = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdLow")));
	public static final int thresholdMid = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdMid")));
	public static final int thresholdHigh = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdHigh")));
	public static final int thresholdMidpointHighMax = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdMidpointHighMax")));
	public static final int thresholdMax = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdMax")));
	//thresholdEffect
	public static final String threshold_Min_ShortEffectId = parseStringSafe(deepGet(configs, "threshold_Min_ShortEffectId"));
	public static final int threshold_Min_ShortEffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "threshold_Min_ShortEffectAmplification")));
	public static final String threshold_Min_LongEffectId = parseStringSafe(deepGet(configs, "threshold_Min_LongEffectId"));
	public static final int threshold_Min_LongEffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "threshold_Min_LongEffectAmplification")));
	public static final String thresholdMin_Low_EffectId = parseStringSafe(deepGet(configs, "thresholdMin_Low_EffectId"));
	public static final int thresholdMin_Low_EffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdMin_Low_EffectAmplification")));
	public static final String thresholdHigh_ShortEffectId = parseStringSafe(deepGet(configs, "thresholdHigh_ShortEffectId"));
	public static final int thresholdHigh_ShortEffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdHigh_ShortEffectAmplification")));
	public static final String thresholdHigh_LongEffectId = parseStringSafe(deepGet(configs, "thresholdHigh_LongEffectId"));
	public static final int thresholdHigh_LongEffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdHigh_LongEffectAmplification")));
	public static final String thresholdHigh_Max_ShortEffectId = parseStringSafe(deepGet(configs, "thresholdHigh_Max_ShortEffectId"));
	public static final int thresholdHigh_Max_ShortAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdHigh_Max_ShortAmplification")));
	public static final String thresholdHigh_Max_LongEffectId = parseStringSafe(deepGet(configs, "thresholdHigh_Max_LongEffectId"));
	public static final int thresholdHigh_Max_LongEffectAmplification = (int) clampValue(parseIntegerSafe(deepGet(configs, "thresholdHigh_Max_LongEffectAmplification")));
	//nutritionModifierSystem
	public static final boolean nutritionModifierEnabled = parseBooleanSafe(deepGet(configs, "nutritionModifierEnabled"));
	public static final float nutritionModifier = (float) clampValue(parseFloatSafe(deepGet(configs, "nutritionModifier")));
	public static final boolean nutritionRandomModifierEnabled = parseBooleanSafe(deepGet(configs, "nutritionRandomModifierEnabled"));
	public static final float nutritionModifierMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "nutritionModifierMinMultiplier")));
	public static final float nutritionModifierMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "nutritionModifierMaxMultiplier")));
	//saturationRandomization
	public static final boolean saturationRandomizationEnabled = parseBooleanSafe(deepGet(configs, "saturationRandomizationEnabled"));
	public static final float saturationMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "saturationMinMultiplier")));
	public static final float saturationMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "saturationMaxMultiplier")));
	//decreaseFoodLevelOverTime
	public static final boolean decreaseFoodLevelOverTimeEnabled = parseBooleanSafe(deepGet(configs, "decreaseFoodLevelOverTimeEnabled"));
	public static final int decreaseFoodLevelBaseTicks = (int) clampValue(parseIntegerSafe(deepGet(configs, "decreaseFoodLevelBaseTicks")));
	public static final int decreaseFoodLevelBaseAmounts = (int) clampValue(parseIntegerSafe(deepGet(configs, "decreaseFoodLevelBaseAmounts")));
	public static final boolean randomDecreaseFoodLevelEnabled = parseBooleanSafe(deepGet(configs, "randomDecreaseFoodLevelEnabled"));
	public static final float randomDecreaseFoodLevelMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "randomDecreaseFoodLevelMinMultiplier")));
	public static final float randomDecreaseFoodLevelMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "randomDecreaseFoodLevelMaxMultiplier")));
	public static final boolean decreaseRandomFoodLevelEnabled = parseBooleanSafe(deepGet(configs, "decreaseRandomFoodLevelEnabled"));
	public static final float decreaseFoodLevelMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "decreaseFoodLevelMinMultiplier")));
	public static final float decreaseFoodLevelMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "decreaseFoodLevelMaxMultiplier")));
	//exhaustionModifierSystem
	public static final boolean exhaustionModifierEnabled = parseBooleanSafe(deepGet(configs, "exhaustionModifierEnabled"));
	public static final float exhaustionModifier = (float) clampValue(parseFloatSafe(deepGet(configs, "exhaustionModifier")));
	public static final float exhaustionCap = (float) clampValue(parseFloatSafe(deepGet(configs, "exhaustionCap")));
	//addExhaustionOverTime
	public static final boolean addExhaustionOverTimeEnabled = parseBooleanSafe(deepGet(configs, "addExhaustionOverTimeEnabled"));
	public static final int addExhaustionBaseTicks = (int) clampValue(parseIntegerSafe(deepGet(configs, "addExhaustionBaseTicks")));
	public static final float addExhaustionBaseAmounts = (float) clampValue(parseFloatSafe(deepGet(configs, "addExhaustionBaseAmounts")));
	public static final boolean randomAddExhaustionEnabled = parseBooleanSafe(deepGet(configs, "randomAddExhaustionEnabled"));
	public static final float randomAddExhaustionMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "randomAddExhaustionMinMultiplier")));
	public static final float randomAddExhaustionMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "randomAddExhaustionMaxMultiplier")));
	public static final boolean addRandomExhaustionEnabled = parseBooleanSafe(deepGet(configs, "addRandomExhaustionEnabled"));
	public static final float addExhaustionMinMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "addExhaustionMinMultiplier")));
	public static final float addExhaustionMaxMultiplier = (float) clampValue(parseFloatSafe(deepGet(configs, "addExhaustionMaxMultiplier")));
	//addPerTickDamage
	public static final boolean addPerTickDamageEnabled = parseBooleanSafe(deepGet(configs, "addPerTickDamageEnabled"));
	public static final String damageStarvingDamageTypeId = parseStringSafe(deepGet(configs, "damageStarvingDamageTypeId"));
	public static final float damageStarvingDamageAmounts = (float) clampValue(parseFloatSafe(deepGet(configs, "damageStarvingDamageAmounts")));
	public static final String damageOvereatingDamageTypeId = parseStringSafe(deepGet(configs, "damageOvereatingDamageTypeId"));
	public static final float damageOvereatingDamageAmounts = (float) clampValue(parseFloatSafe(deepGet(configs, "damageOvereatingDamageAmounts")));
	//addPerTickRealDamage
	public static final boolean addPerTickRealDamageEnabled = parseBooleanSafe(deepGet(configs, "addPerTickRealDamageEnabled"));
	public static final float realDamageStarvingAmounts = (float) clampValue(parseFloatSafe(deepGet(configs, "realDamageStarvingAmounts")));
	public static final float realDamageOvereatingAmounts = (float) clampValue(parseFloatSafe(deepGet(configs, "realDamageOvereatingAmounts")));


    private ConfigHelper() {}

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
