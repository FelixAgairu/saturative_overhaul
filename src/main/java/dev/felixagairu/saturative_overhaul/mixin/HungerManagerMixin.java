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
import dev.felixagairu.saturative_overhaul.registries.DamageTypeRegistry;
import dev.felixagairu.saturative_overhaul.util.ConfigData;
import dev.felixagairu.saturative_overhaul.util.DamageHelper;
import dev.felixagairu.saturative_overhaul.util.LimitRandomizer;
import net.minecraft.entity.player.HungerManager;

/*? <=1.21.1 {*/
/*import net.minecraft.entity.player.PlayerEntity;
*//*?} else {*/
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
/*?}*/

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;

/*? <=1.21.10 {*/
import net.minecraft.world.GameRules;
/*?} else {*/
/*import net.minecraft.world.rule.GameRules;
*//*?}*/

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.felixagairu.saturative_overhaul.util.EffectHelper.gainEffect;

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
    private static final int maxFoodLevel = ConfigData.maxFoodLevel;
    @Unique
    private static final float defaultSaturationLevel = 2.0f;
    @Unique
    private static final float defaultExhaustionLevel = 0.0f;
    @Unique
    private static final int defaultFoodLevel = ConfigData.defaultFoodLevel;
    @Unique
    private static final float midpointHighMax = (float) (ConfigData.thresholdHigh + ConfigData.maxFoodLevel) / 2;
    @Unique
    private static final float midpointLowMid = (float) (ConfigData.thresholdLow + ConfigData.thresholdMid) / 2;
    @Unique
    private static final float midpointMinLow = (float) (ConfigData.thresholdMin + ConfigData.thresholdLow) / 2;


    @Shadow
    private int foodLevel = defaultFoodLevel;
    @Shadow
    private float saturationLevel = defaultSaturationLevel;
    @Shadow
    private float exhaustion = defaultExhaustionLevel;
    @Shadow
    private int foodTickTimer;
    /*? <=1.21.1 {*/
    /*@Shadow
    private int prevFoodLevel;
    *//*?}*/


    /**
     * Inject to addInternal():
     * MathHelper.clamp(nutrition + this.foodLevel, 0, 20);
     *
     * @return Clamped foodLevel level
     *
     * @param nutritionAdded The nutrition form caller
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
    private int foodLevelChange(int nutritionAdded, int min, int max) {
        float nutrition = nutritionAdded - foodLevel;
        float multiNutrition = nutrition * ConfigData.nutritionModifier;
        float randomizedMultiNutrition = LimitRandomizer.generateRandom(multiNutrition, ConfigData.nutritionRandLow, ConfigData.nutritionRandHigh);
        int iRandomizedMultiNutrition = Math.round(randomizedMultiNutrition);
        // The way change the max food level
        foodLevel = MathHelper.clamp(iRandomizedMultiNutrition + foodLevel, 0, maxFoodLevel);
        return foodLevel;
    }

    @Redirect(
            method = "addInternal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"
            )
    )
    private float saturationLevelChange(float saturationAdded, float min, float max) {
        float randomizedSaturationAdded = LimitRandomizer.generateRandom(saturationAdded, ConfigData.saturationRandLow, ConfigData.saturationRandHigh);
        saturationLevel = MathHelper.clamp(randomizedSaturationAdded, 0.0F, (float) foodLevel / 18.0F);
        return saturationLevel;
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
    public void modHeadUpdate(/*? <=1.21.1 {*//*PlayerEntity*//*?} else {*/ServerPlayerEntity/*?}*/ player, CallbackInfo ci) {
        /*? <=1.21.1 {*/
        /*Difficulty difficulty = player.getWorld().getDifficulty();

        boolean naturalRegenerative = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);

        prevFoodLevel = foodLevel;
        *//*?} else <=1.21.5 {*/
        /*ServerWorld serverWorld = player.getServerWorld();
        Difficulty difficulty = serverWorld.getDifficulty();

        boolean naturalRegenerative = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        *//*?} else <=1.21.11 {*/
        ServerWorld serverWorld = player.getEntityWorld();
        Difficulty difficulty = serverWorld.getDifficulty();

        boolean naturalRegenerative = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        /*?} else {*//*
        *//*?}*/

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
                gainEffect(player, ConfigData.thresholdHigh_Max_ShortEffectId, 60, ConfigData.thresholdHigh_Max_ShortAmplification, "SLOWNESS");
                foodTickTimer += 1;
                if (foodTickTimer >= (80)) {
                    gainEffect(player,ConfigData.thresholdHigh_Max_LongEffectId , 200, ConfigData.thresholdHigh_Max_LongEffectAmplification, "NAUSEA");
                    exhaustion = Math.max(0.0F, exhaustion + 3.9F);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            if (ConfigData.addRealDamageEnabled) {
                                DamageHelper.doEffectDamage(player, ConfigData.damagePreTickEffectsOvereating, DamageTypeRegistry.OVEREATING_DAMAGE_TYPE);
                                DamageHelper.doRealDamage(player, ConfigData.damagePreTickOvereating);
                            } else {
                                DamageHelper.doEffectDamage(player, ConfigData.damageEffectsOvereating, DamageTypeRegistry.OVEREATING_DAMAGE_TYPE);
                            }
                        }
                    }
                    foodTickTimer = 0;
                }
            } else if (foodLevel >= ConfigData.thresholdHigh) {
                // Food Bar Overeating
                // "Overeat I"
                gainEffect(player,ConfigData.thresholdHigh_ShortEffectId , 60, ConfigData.thresholdHigh_ShortEffectAmplification, "SLOWNESS");
                foodTickTimer += 1;
                if (foodTickTimer >= (100)) {
                    gainEffect(player,ConfigData.thresholdHigh_LongEffectId , 160, ConfigData.thresholdHigh_LongEffectAmplification, "NAUSEA");
                    exhaustion = Math.max(0.0F, exhaustion + 2.5F);
                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && foodCurable && saturationLevel > 0.0F &&
                    foodLevel >= ConfigData.thresholdMid) {
                foodTickTimer += 1;
                if (foodTickTimer >= (10)) {
                    float f = Math.min(saturationLevel, 5.0F);
                    player.heal(f / 5.0F);
                    exhaustion = Math.max(0.0F, exhaustion + (f / 2.0F));

                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && foodCurable && saturationLevel <= 0.0F &&
                    foodLevel >= ConfigData.thresholdLow &&
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
            } else if (foodLevel >= ConfigData.thresholdMin && foodLevel <= midpointMinLow) {
                // "Hunger I"
                gainEffect(player, ConfigData.thresholdMin_Low_EffectId, 60, ConfigData.thresholdMin_Low_EffectAmplification, "SLOWNESS");
            } else if (foodLevel < ConfigData.thresholdMin) {
                // Food Bar Starving
                // "Hunger II"
                gainEffect(player, ConfigData.threshold_Min_ShortEffectId, 60, ConfigData.threshold_Min_ShortEffectAmplification, "SLOWNESS");
                foodTickTimer += 1;
                if (foodTickTimer >= (10 + foodLevel * 2)) {
                    gainEffect(player, ConfigData.threshold_Min_LongEffectId, 120, ConfigData.threshold_Min_LongEffectAmplification, "NAUSEA");
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            if (ConfigData.addRealDamageEnabled) {
                                DamageHelper.doEffectDamage(player, ConfigData.damagePreTickEffectsStarving, DamageTypeRegistry.STARVING_DAMAGE_TYPE);
                                DamageHelper.doRealDamage(player, ConfigData.damagePreTickStarving);
                            } else {
                                DamageHelper.doEffectDamage(player, ConfigData.damageEffectsStarving, DamageTypeRegistry.STARVING_DAMAGE_TYPE);
                            }
                        }
                    }

                    foodTickTimer = 0;
                }
            }
        } else {
            if (foodLevel > ConfigData.defaultFoodLevel) {
                foodLevel = ConfigData.defaultFoodLevel;
            } else {
                foodLevel = defaultFoodLevel;
            }
        }
        //updateValues();
    }

    // Add global modifier "exhaustionModifier" to exhaustion for match the config's "nutritionModifier"
    @Inject(
            method = "addExhaustion",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modHeadAddExhaustion(float exhaustion, CallbackInfo ci) {
        this.exhaustion = Math.min(this.exhaustion + exhaustion * ConfigData.exhaustionModifier, ConfigData.exhaustionCap);
        ci.cancel();
    }
}
