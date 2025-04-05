package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.emilahmaboy.felixagairu.saturative_overhaul.common.registries.DamageTypeRegistry;
import dev.emilahmaboy.felixagairu.saturative_overhaul.tools.LimitRandomizer;
import dev.emilahmaboy.felixagairu.saturative_overhaul.tools.NbtUpdater;
import dev.emilahmaboy.felixagairu.saturative_overhaul.tools.SharedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Unique
    private static final int maxFoodLevel = SharedData.foodLevel;
    @Unique
    private static final float defaultSaturationLevel = 2.0f;
    @Unique
    private static final float defaultExhaustionLevel = 0.0f;
    @Unique
    private static final int defaultFoodLevel = SharedData.defaultFoodLevel;

    @Shadow
    private int foodLevel = defaultFoodLevel;
    @Shadow
    private float saturationLevel = defaultSaturationLevel;
    @Shadow
    private float exhaustion = defaultExhaustionLevel;
    @Shadow
    private int foodTickTimer = NbtUpdater.foodTickTimer;
    @Shadow
    private int prevFoodLevel;


    /**
     * @return
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */

    @Unique
    private void gainEffect(PlayerEntity who, @NotNull String eff, int dur, int amp) {
        switch (eff) {
            case "SLOWNESS":
                who.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, dur, amp, false, false));
                break;
            case "NAUSEA":
                who.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, dur, amp, false, false));
                break;
        }
    }

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    private int foodLevelChange(int nutritionAdded, int min, int max) {
        float nutrition = nutritionAdded - foodLevel;
        float multiNutrition = nutrition * SharedData.nutritionModifier;
        float randomizedMultiNutrition = LimitRandomizer.fTheRandom(multiNutrition, SharedData.nutritionRandLow, SharedData.nutritionRandHigh);
        int iRandomizedMultiNutrition = Math.round(randomizedMultiNutrition);
        foodLevel = MathHelper.clamp(iRandomizedMultiNutrition + foodLevel, 0, maxFoodLevel);
        return foodLevel;
    }

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float saturationLevelChange(float saturationAdded, float min, float max) {
        float randomizedSaturationAdded = LimitRandomizer.fTheRandom(saturationAdded, SharedData.saturationRandLow, SharedData.saturationRandHigh);
        saturationLevel = MathHelper.clamp(randomizedSaturationAdded, 0.0F, (float) foodLevel / 18.0F);
        return saturationLevel;
    }

    @ModifyReturnValue(method = "isNotFull", at = @At("TAIL"))
    public boolean isNotFull(boolean original) {
        return foodLevel < maxFoodLevel;
    }

    /**
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */
/*
    @Inject(method = "update", at = @At("TAIL"))
    public void modAfterUpdate(PlayerEntity player, CallbackInfo ci) {
        updateValues();
    }
*/
    @Inject(method = "update", at = @At("HEAD"))
    public void modBeforeUpdate(PlayerEntity player, CallbackInfo ci) {
        Difficulty difficulty = player.getWorld().getDifficulty();

        boolean naturalRegenerative = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        prevFoodLevel = foodLevel;

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

            if (foodLevel >= SharedData.thresholdHigh + (SharedData.foodLevel - SharedData.thresholdHigh) / 2 ) {
                gainEffect(player, "SLOWNESS",60,1);
                foodTickTimer += 1;
                if (foodTickTimer >= (80)) {
                    gainEffect(player, "NAUSEA",200,0);
                    exhaustion = Math.max(0.0F, exhaustion + 3.9F);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(DamageTypeRegistry.getSource(player.getWorld(), DamageTypeRegistry.OVEREATING_DAMAGE_TYPE), 1.0F);
                        }
                    }
                    foodTickTimer = 0;
                }
            } else if (foodLevel >= SharedData.thresholdHigh) {
                gainEffect(player, "SLOWNESS",60,0);
                foodTickTimer += 1;
                if (foodTickTimer >= (100)) {
                    gainEffect(player, "NAUSEA",160,0);
                    exhaustion = Math.max(0.0F, exhaustion + 2.5F);
                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && saturationLevel > 0.0F && player.canFoodHeal() &&
                    foodLevel >= SharedData.thresholdMid &&
                    foodLevel < SharedData.thresholdHigh) {
                foodTickTimer += 1;
                if (foodTickTimer >= (10)) {
                    float f = Math.min(saturationLevel, 5.0F);
                    player.heal(f / 5.0F);
                    exhaustion = Math.max(0.0F, exhaustion + (f / 2.0F));

                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && saturationLevel <= 0.0F && player.canFoodHeal() &&
                    foodLevel >= SharedData.thresholdLow &&
                    player.getHealth() <= player.getMaxHealth() / 1.15) {
                foodTickTimer += 1;
                if (foodTickTimer >= (3)) {
                    foodLevel -= 10;
                    saturationLevel += 3.5F;
                    exhaustion = Math.max(0.0F, exhaustion + 0.2F);

                    foodTickTimer = 0;
                }
            } else if (naturalRegenerative && player.canFoodHeal() &&
                    foodLevel >= SharedData.thresholdMin + (SharedData.thresholdLow - SharedData.thresholdMin) / 2 &&
                    foodLevel < SharedData.thresholdLow + (SharedData.thresholdMid - SharedData.thresholdLow) / 2) {
                foodTickTimer += 1;
                if (foodTickTimer >= (55)) {
                    player.heal(1);
                    exhaustion = Math.max(0.0F, exhaustion + 2.3F);

                    foodTickTimer = 0;
                }
            } else if (foodLevel >= SharedData.thresholdMin &&
                    foodLevel <= SharedData.thresholdMin + (SharedData.thresholdLow - SharedData.thresholdMin) / 2) {
                gainEffect(player, "SLOWNESS",60,0);
            } else if (foodLevel < SharedData.thresholdMin) {
                gainEffect(player, "SLOWNESS",60,1);
                foodTickTimer += 1;
                if (foodTickTimer >= (40 + foodLevel * 2)) {
                    gainEffect(player, "NAUSEA",120,0);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(player.getDamageSources().starve(), 1.0F);
                        }
                    }

                    foodTickTimer = 0;
                }
            }
        }
        //updateValues();
    }
}
