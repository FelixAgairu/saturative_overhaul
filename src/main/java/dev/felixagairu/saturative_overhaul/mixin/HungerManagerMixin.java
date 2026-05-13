/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
// This file includes modifications by Felix.
// Licensed under the MPL 2.0. Original portions © saturative upstream under MIT by EmilAhmaBoy.


package dev.felixagairu.saturative_overhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.felixagairu.saturative_overhaul.util.*;
import net.minecraft.entity.player.HungerManager;

/*? >=1.21.2 {*/
/*import net.minecraft.server.network.ServerPlayerEntity;
*//*?} else >= 1.21.1 {*/
import net.minecraft.entity.player.PlayerEntity;
/*?}*/

import net.minecraft.world.Difficulty;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author EmilAhmaBoy
 * @reason Saturative mod overwrites HungerManager
 */

/**
 * @author FelixAgairu
 * @changes Using @Inject to update()
 * @reason Low compatibility of the @Overwrite
 */
@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Unique
    private final int maxFoodLevel = ConfigHelper.thresholdMax;
    @Unique
    private final float defaultSaturationLevel = 2.0f;
    @Unique
    private static final float defaultExhaustionLevel = 0.0f;
    @Unique
    private static final int defaultFoodLevel = ConfigHelper.defaultFoodLevel;
    @Unique
    private static final float midpointHighMax = (float) (ConfigHelper.thresholdHigh + ConfigHelper.thresholdMax) / 2;
    @Unique
    private static final float midpointLowMid = (float) (ConfigHelper.thresholdLow + ConfigHelper.thresholdMid) / 2;
    @Unique
    private static final float midpointMinLow = (float) (ConfigHelper.thresholdMin + ConfigHelper.thresholdLow) / 2;

    @Shadow
    private int foodLevel = defaultFoodLevel;
    @Shadow
    private float saturationLevel = defaultSaturationLevel;
    @Shadow
    private float exhaustion = defaultExhaustionLevel;
    @Shadow
    private int foodTickTimer;
    /*? 1.21.1 {*/
    @Shadow
    private int prevFoodLevel;
    /*?}*/

    /**
     * Inject to addInternal():
     * <br>
     * MathHelper.clamp(nutrition + this.foodLevel, 0, 20);
     *
     * @return Clamped food level multiply by modifier
     *
     * @param foodLevelAdded The nutrition form caller
     * @param min clamp min
     * @param max clamp max
     */
    @Redirect(
            method = "addInternal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"
            )
    )
    private int addNutritionModifier(int foodLevelAdded, int min, int max) {
        if (ConfigHelper.nutritionModifierEnabled) {
            int finalFoodLevel;
            float nutrition = foodLevelAdded - foodLevel;

            if (ConfigHelper.nutritionRandomModifierEnabled) {
                float randomModifier = MathHelper.generateRandom(
                        ConfigHelper.nutritionModifier,
                        ConfigHelper.nutritionModifierMinMultiplier,
                        ConfigHelper.nutritionModifierMaxMultiplier
                );
                finalFoodLevel = Math.round(foodLevel + nutrition * randomModifier);
            } else {
                finalFoodLevel = Math.round(foodLevel * ConfigHelper.nutritionModifier);
            }

            return MathHelper.clampFoodLevel(finalFoodLevel);
        } else {
            // Vanilla like
            return net.minecraft.util.math.MathHelper.clamp(foodLevelAdded, 0, maxFoodLevel);
        }
    }

    @Redirect(
            method = "addInternal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"
            )
    )
    private float saturationLevelChange(float saturationAdded, float min, float max) {
        if (ConfigHelper.saturationRandomizationEnabled) {
            float saturation = saturationAdded - saturationLevel;
            float randomizedSaturationAdded = MathHelper.generateRandom(saturation, ConfigHelper.saturationMinMultiplier, ConfigHelper.saturationMaxMultiplier);
            return net.minecraft.util.math.MathHelper.clamp(randomizedSaturationAdded, 0.0f, ConfigHelper.thresholdMax);
        } else {
            // Vanilla
            return net.minecraft.util.math.MathHelper.clamp(saturationAdded, 0.0f, max);
        }
    }

    @ModifyReturnValue(
            method = "isNotFull",
            at = @At("TAIL")
    )
    public boolean isNotFull(boolean original) {
        return foodLevel < maxFoodLevel;
    }

    @Inject(
            method = "update",
            at = @At("HEAD")
    )
    public void modHeadUpdate(/*? >=1.21.2 {*//*ServerPlayerEntity*//*?} else >=1.21.1 {*/PlayerEntity/*?}*/ player, CallbackInfo ci) {
        Difficulty difficulty = WorldPlayerUtils.getDifficulty(player);
        boolean naturalRegenerative = WorldPlayerUtils.isNaturalRegenerative(player);
        /*? 1.21.1 {*/
        prevFoodLevel = foodLevel;
        /*?}*/

        boolean foodCurable = player.canFoodHeal();

        if (!player.isCreative()) {
            if (player.getHealth() < player.getMaxHealth()) {
                exhaustion += Math.max(0.0F, 0.002F + (foodLevel > 300 ? 0.002F : 0.0F));
            }

            if (exhaustion > 1.5F) {
                exhaustion -= 1.5F;
                if (saturationLevel > 0.0F) {
                    saturationLevel = (Math.max(saturationLevel - 0.4F, 0.0F));
                    if (saturationLevel > 0.0F && foodLevel > 200) {
                        saturationLevel = (Math.max(saturationLevel - 0.4F, 0.0F));
                    }
                }
                if (saturationLevel <= 0.0F) {
                    foodLevel = (Math.max(foodLevel - 2, 0));
                } else if (saturationLevel < 3.0F) {
                    foodLevel = (Math.max(foodLevel - 1, 0));
                }
            }
            if (foodLevel >= midpointHighMax) {
                // Food Bar Overeating+
                // "Overeat II"
                EffectHelper.gainEffect(
                        player,
                        ConfigHelper.thresholdHigh_Max_ShortEffectId,
                        60,
                        ConfigHelper.thresholdHigh_Max_ShortAmplification,
                        "minecraft:slowness"
                );
                foodTickTimer += 1;
                if (foodTickTimer >= (80)) {
                    EffectHelper.gainEffect(
                            player,
                            ConfigHelper.thresholdHigh_Max_LongEffectId,
                            200, ConfigHelper.thresholdHigh_Max_LongEffectAmplification,
                            "minecraft:nausea"
                    );
                    exhaustion = Math.max(0.0F, exhaustion + 3.9F);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            if (ConfigHelper.addPerTickDamageEnabled) {
                                DamageHelper.doEffectDamage(
                                        WorldPlayerUtils.getWorld(player),
                                        player,
                                        ConfigHelper.damageOvereatingDamageTypeId,
                                        ConfigHelper.damageOvereatingDamageAmounts,
                                        "minecraft:starve"
                                );
                            }
                            if (ConfigHelper.addPerTickRealDamageEnabled) {
                                DamageHelper.doRealDamage(
                                        player,
                                        ConfigHelper.realDamageOvereatingAmounts
                                );
                            }
                        }
                    }
                    foodTickTimer = 0;
                }
            } else if (foodLevel >= ConfigHelper.thresholdHigh) {
                // Food Bar Overeating
                // "Overeat I"
                EffectHelper.gainEffect(
                        player,
                        ConfigHelper.thresholdHigh_ShortEffectId,
                        60,
                        ConfigHelper.thresholdHigh_ShortEffectAmplification,
                        "minecraft:slowness"
                );
                foodTickTimer += 1;
                if (foodTickTimer >= (100)) {
                    EffectHelper.gainEffect(
                            player,
                            ConfigHelper.thresholdHigh_LongEffectId,
                            160,
                            ConfigHelper.thresholdHigh_LongEffectAmplification,
                            "minecraft:nausea"
                    );
                    exhaustion = Math.max(0.0F, exhaustion + 2.5F);
                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && foodCurable && saturationLevel > 0.0F &&
                    foodLevel >= ConfigHelper.thresholdMid) {
                foodTickTimer += 1;
                if (foodTickTimer >= (10)) {
                    float f = Math.min(saturationLevel, 5.0F);
                    player.heal(f / 5.0F);
                    exhaustion = Math.max(0.0F, exhaustion + (f / 2.0F));

                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && foodCurable && saturationLevel <= 0.0F &&
                    foodLevel >= ConfigHelper.thresholdLow &&
                    player.getHealth() <= player.getMaxHealth() / 1.15) {
                foodTickTimer += 1;
                if (foodTickTimer >= (3)) {
                    foodLevel -= 10;
                    saturationLevel += 3.5F;
                    exhaustion = Math.max(0.0F, exhaustion + 0.2F);

                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && foodCurable &&
                    foodLevel >= midpointMinLow &&
                    foodLevel < midpointLowMid) {
                foodTickTimer += 1;
                if (foodTickTimer >= (55)) {
                    player.heal(1);
                    exhaustion = Math.max(0.0F, exhaustion + 2.3F);

                    foodTickTimer = 0;
                }
            } else if (foodLevel >= ConfigHelper.thresholdMin && foodLevel <= midpointMinLow) {
                // "Hunger I"
                EffectHelper.gainEffect(
                        player,
                        ConfigHelper.thresholdMin_Low_EffectId,
                        60, ConfigHelper.thresholdMin_Low_EffectAmplification,
                        "minecraft:slowness"
                );
            } else if (foodLevel < ConfigHelper.thresholdMin) {
                // Food Bar Starving
                // "Hunger II"
                EffectHelper.gainEffect(
                        player,
                        ConfigHelper.threshold_Min_ShortEffectId,
                        60, ConfigHelper.threshold_Min_ShortEffectAmplification,
                        "minecraft:slowness"
                );
                foodTickTimer += 1;
                if (foodTickTimer >= (10 + foodLevel * 2)) {
                    EffectHelper.gainEffect(
                            player,
                            ConfigHelper.threshold_Min_LongEffectId,
                            120,
                            ConfigHelper.threshold_Min_LongEffectAmplification,
                            "minecraft:nausea"
                    );
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            if (ConfigHelper.addPerTickDamageEnabled) {
                                DamageHelper.doEffectDamage(
                                        WorldPlayerUtils.getWorld(player),
                                        player,
                                        ConfigHelper.damageStarvingDamageTypeId,
                                        ConfigHelper.damageStarvingDamageAmounts,
                                        "minecraft:starve"
                                );
                            }
                            if (ConfigHelper.addPerTickRealDamageEnabled) {
                                DamageHelper.doRealDamage(
                                        player,
                                        ConfigHelper.realDamageStarvingAmounts
                                );
                            }
                        }
                    }

                    foodTickTimer = 0;
                }
            }
        } else {
            if (foodLevel > ConfigHelper.defaultFoodLevel) {
                foodLevel = ConfigHelper.defaultFoodLevel;
            } else {
                foodLevel = defaultFoodLevel;
            }
        }
        //updateValues();
    }

    /**
     * Inject to addExhaustion():
     * <br>
     * Math.min(this.exhaustion + exhaustion, 40.0F);
     *
     * @return Clamped exhaustion multiply by modifier
     *
     * @param exhaustionAdded The exhaustion form caller
     * @param max clamp max
     */
    @Redirect(
            method = "addExhaustion",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(FF)F"
            )
    )
    private float setExhaustionModifier(float exhaustionAdded, float max) {
        if (ConfigHelper.exhaustionModifierEnabled) {
            float exhaustion = exhaustionAdded - this.exhaustion;
            return Math.min(this.exhaustion + exhaustion * ConfigHelper.exhaustionModifier, ConfigHelper.exhaustionCap);
        } else {
            // Vanilla
            return Math.min(exhaustionAdded, 40.0f);
        }
    }
}
