/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

public class ConfigDefault {
    protected static final String defaultConfigs = """
    {
        "defaultFoodLevel_": "config.desc_defaultFoodLevel",
        "defaultFoodLevel": "280",
        "guiStarving_": "config.desc_guiStarving",
        "guiStarving": "75",
        "guiNormal_": "config.desc_guiNormal",
        "guiNormal": "280",
        "guiOvereating_": "config.desc_guiOvereating",
        "guiOvereating": "350",
        "threshold_": "config.desc_threshold",
        "threshold": {
            "thresholdMin_": "config.desc_thresholdMin",
            "thresholdMin": "25",
            "thresholdLow_": "config.desc_thresholdLow",
            "thresholdLow": "100",
            "thresholdMid_": "config.desc_thresholdMid",
            "thresholdMid": "280",
            "thresholdHigh_": "config.desc_thresholdHigh",
            "thresholdHigh": "350",
            "thresholdMidpointHighMax_": "config.desc_thresholdMidpointHighMax",
            "thresholdMidpointHighMax": "375",
            "thresholdMax_": "config.desc_thresholdMax",
            "thresholdMax": "400"
        },
        "thresholdEffect_": "config.desc_thresholdEffect",
        "thresholdEffect": {
            "threshold_Min_ShortEffectId_": "config.desc_threshold_Min_ShortEffectId",
            "threshold_Min_ShortEffectId": "minecraft:slowness",
            "threshold_Min_ShortEffectAmplification_": "config.desc_threshold_Min_ShortEffectAmplification",
            "threshold_Min_ShortEffectAmplification": "1",
            "threshold_Min_LongEffectId_": "config.desc_threshold_Min_LongEffectId",
            "threshold_Min_LongEffectId": "minecraft:nausea",
            "threshold_Min_LongEffectAmplification_": "config.desc_threshold_Min_LongEffectAmplification",
            "threshold_Min_LongEffectAmplification": "0",
            "thresholdMin_Low_EffectId_": "config.desc_thresholdMin_Low_EffectId",
            "thresholdMin_Low_EffectId": "minecraft:slowness",
            "thresholdMin_Low_EffectAmplification_": "config.desc_thresholdMin_Low_EffectAmplification",
            "thresholdMin_Low_EffectAmplification": "0",
            "thresholdHigh_ShortEffectId_": "config.desc_thresholdHigh_ShortEffectId",
            "thresholdHigh_ShortEffectId": "minecraft:slowness",
            "thresholdHigh_ShortEffectAmplification_": "config.desc_thresholdHigh_ShortEffectAmplification",
            "thresholdHigh_ShortEffectAmplification": "0",
            "thresholdHigh_LongEffectId_": "config.desc_thresholdHigh_LongEffectId",
            "thresholdHigh_LongEffectId": "minecraft:nausea",
            "thresholdHigh_LongEffectAmplification_": "config.desc_thresholdHigh_LongEffectAmplification",
            "thresholdHigh_LongEffectAmplification": "0",
            "thresholdHigh_Max_ShortEffectId_": "config.desc_thresholdHigh_Max_ShortEffectId",
            "thresholdHigh_Max_ShortEffectId": "minecraft:slowness",
            "thresholdHigh_Max_ShortAmplification_": "config.desc_thresholdHigh_Max_ShortAmplification",
            "thresholdHigh_Max_ShortAmplification": "1",
            "thresholdHigh_Max_LongEffectId_": "config.desc_thresholdHigh_Max_LongEffectId",
            "thresholdHigh_Max_LongEffectId": "minecraft:nausea",
            "thresholdHigh_Max_LongEffectAmplification_": "config.desc_thresholdHigh_Max_LongEffectAmplification",
            "thresholdHigh_Max_LongEffectAmplification": "0"
        },
        "nutritionModifierSystem_": "config.desc_nutritionModifierSystem",
        "nutritionModifierSystem": {
            "nutritionModifierEnabled_": "config.desc_nutritionModifierEnabled",
            "nutritionModifierEnabled": "true",
            "nutritionModifier_": "config.desc_nutritionModifier",
            "nutritionModifier": "5",
            "nutritionRandomModifierEnabled_": "config.desc_nutritionRandomModifierEnabled",
            "nutritionRandomModifierEnabled": "true",
            "nutritionModifierMinMultiplier_": "config.desc_nutritionModifierMinMultiplier",
            "nutritionModifierMinMultiplier": "0.75",
            "nutritionModifierMaxMultiplier_": "config.desc_nutritionModifierMaxMultiplier",
            "nutritionModifierMaxMultiplier": "1.25"
        },
        "saturationRandomization_": "config.desc_saturationRandomization",
        "saturationRandomization": {
            "saturationRandomizationEnabled_": "config.desc_saturationRandomizationEnabled",
            "saturationRandomizationEnabled": "true",
            "saturationMinMultiplier_": "config.desc_saturationMinMultiplier",
            "saturationMinMultiplier": "0.75",
            "saturationMaxMultiplier_": "config.desc_saturationMaxMultiplier",
            "saturationMaxMultiplier": "1.25"
        },
        "decreaseFoodLevelOverTime_": "config.desc_decreaseFoodLevelOverTime",
        "decreaseFoodLevelOverTime": {
            "decreaseFoodLevelOverTimeEnabled_": "config.desc_decreaseFoodLevelOverTimeEnabled",
            "decreaseFoodLevelOverTimeEnabled": "true",
            "decreaseFoodLevelBaseTicks_": "config.desc_decreaseFoodLevelBaseTicks",
            "decreaseFoodLevelBaseTicks": "200",
            "decreaseFoodLevelBaseAmounts_": "config.desc_decreaseFoodLevelBaseAmounts",
            "decreaseFoodLevelBaseAmounts": "1",
            "randomDecreaseFoodLevelEnabled_": "config.desc_randomDecreaseFoodLevelEnabled",
            "randomDecreaseFoodLevelEnabled": "true",
            "randomDecreaseFoodLevelMinMultiplier_": "config.desc_randomDecreaseFoodLevelMinMultiplier",
            "randomDecreaseFoodLevelMinMultiplier": "0.75",
            "randomDecreaseFoodLevelMaxMultiplier_": "config.desc_randomDecreaseFoodLevelMaxMultiplier",
            "randomDecreaseFoodLevelMaxMultiplier": "1.25",
            "decreaseRandomFoodLevelEnabled_": "config.desc_decreaseRandomFoodLevelEnabled",
            "decreaseRandomFoodLevelEnabled": "true",
            "decreaseFoodLevelMinMultiplier_": "config.desc_decreaseFoodLevelMinMultiplier",
            "decreaseFoodLevelMinMultiplier": "0.75",
            "decreaseFoodLevelMaxMultiplier_": "config.desc_decreaseFoodLevelMaxMultiplier",
            "decreaseFoodLevelMaxMultiplier": "1.25"
        },
        "exhaustionModifierSystem_": "config.desc_exhaustionModifierSystem",
        "exhaustionModifierSystem": {
            "exhaustionModifierEnabled_": "config.desc_exhaustionModifierEnabled",
            "exhaustionModifierEnabled": "true",
            "exhaustionModifier_": "config.desc_exhaustionModifier",
            "exhaustionModifier": "3.5",
            "exhaustionCap_": "config.desc_exhaustionCap",
            "exhaustionCap": "60"
        },
        "addExhaustionOverTime_": "config.desc_addExhaustionOverTime",
        "addExhaustionOverTime": {
            "addExhaustionOverTimeEnabled_": "config.desc_addExhaustionOverTimeEnabled",
            "addExhaustionOverTimeEnabled": "true",
            "addExhaustionBaseTicks_": "config.desc_addExhaustionBaseTicks",
            "addExhaustionBaseTicks": "200",
            "addExhaustionBaseAmounts_": "config.desc_addExhaustionBaseAmounts",
            "addExhaustionBaseAmounts": "0.0234375",
            "randomAddExhaustionEnabled_": "config.desc_randomAddExhaustionEnabled",
            "randomAddExhaustionEnabled": "true",
            "randomAddExhaustionMinMultiplier_": "config.desc_randomAddExhaustionMinMultiplier",
            "randomAddExhaustionMinMultiplier": "0.75",
            "randomAddExhaustionMaxMultiplier_": "config.desc_randomAddExhaustionMaxMultiplier",
            "randomAddExhaustionMaxMultiplier": "1.25",
            "addRandomExhaustionEnabled_": "config.desc_addRandomExhaustionEnabled",
            "addRandomExhaustionEnabled": "true",
            "addExhaustionMinMultiplier_": "config.desc_addExhaustionMinMultiplier",
            "addExhaustionMinMultiplier": "0.75",
            "addExhaustionMaxMultiplier_": "config.desc_addExhaustionMaxMultiplier",
            "addExhaustionMaxMultiplier": "1.25"
        },
        "addPerTickDamage_": "config.desc_addPerTickDamage",
        "addPerTickDamage": {
            "addPerTickDamageEnabled_": "config.desc_addPerTickDamageEnabled",
            "addPerTickDamageEnabled": "false",
            "damageStarvingDamageTypeId_": "config.desc_damageStarvingDamageTypeId",
            "damageStarvingDamageTypeId": "minecraft:starve",
            "damageStarvingDamageAmounts_": "config.desc_damageStarvingDamageAmounts",
            "damageStarvingDamageAmounts": "0.0234375",
            "damageOvereatingDamageTypeId_": "config.desc_damageOvereatingDamageTypeId",
            "damageOvereatingDamageTypeId": "minecraft:starve",
            "damageOvereatingDamageAmounts_": "config.desc_damageOvereatingDamageAmounts",
            "damageOvereatingDamageAmounts": "0.0234375"
        },
        "addPerTickRealDamage_": "config.desc_addPerTickRealDamage",
        "addPerTickRealDamage": {
            "addPerTickRealDamageEnabled_": "config.desc_addPerTickRealDamageEnabled",
            "addPerTickRealDamageEnabled": "false",
            "realDamageStarvingAmounts_": "config.desc_realDamageStarvingAmounts",
            "realDamageStarvingAmounts": "0.0078125",
            "realDamageOvereatingAmounts_": "config.desc_realDamageOvereatingAmounts",
            "realDamageOvereatingAmounts": "0.0078125"
        }
    }

    """;
    private ConfigDefault() {}
}
