package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.emilahmaboy.felixagairu.saturative_overhaul.api.Helper;
import dev.emilahmaboy.felixagairu.saturative_overhaul.api.TheRandom;
import dev.emilahmaboy.felixagairu.saturative_overhaul.common.registries.DamageTypeRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
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
    private final int maxFoodLevel = 400;
    @Unique
    private final float defaultSaturationLevel = 2.0f;
    @Unique
    private final float defaultExhaustionLevel = 0.0f;
    @Unique
    private final int defaultFoodLevel = 160;

    // Too low food: <20
    // Low food: <100
    // Normal: <300
    // Too saturated: >=300

    @Shadow
    private int foodLevel = defaultFoodLevel;
    @Shadow
    private float saturationLevel = defaultSaturationLevel;
    @Shadow
    private float exhaustion = defaultExhaustionLevel;
    @Shadow
    private int foodTickTimer = Helper.foodTickTimer;
    @Shadow
    private int prevFoodLevel;


    /**
     * @return
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    private int foodLevelChange(int nutritionAdded, int min, int max) {
        float nutrition = nutritionAdded - this.foodLevel;
        float multiNutrition = nutrition * Helper.nutritionModifier;
        float randomizedMultiNutrition = TheRandom.fTheRandom(multiNutrition, 0.888F, 1.222F);
        int iRandomizedMultiNutrition = Math.round(randomizedMultiNutrition);
        this.foodLevel = MathHelper.clamp(iRandomizedMultiNutrition + this.foodLevel, 0, this.maxFoodLevel);
        return this.foodLevel;
    }

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float saturationLevelChange(float saturationAdded, float min, float max) {
        float randomizedSaturationAdded = TheRandom.fTheRandom(saturationAdded, 0.8F, 1.2F);
        this.saturationLevel = MathHelper.clamp(randomizedSaturationAdded, 0.0F, (float) this.foodLevel / 18.0F);
        return this.saturationLevel;
    }

    @ModifyReturnValue(method = "isNotFull", at = @At("TAIL"))
    public boolean isNotFull(boolean original) {
        return this.foodLevel < maxFoodLevel;
    }

    /**
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */
/*
    @Inject(method = "update", at = @At("TAIL"))
    public void modAfterUpdate(PlayerEntity player, CallbackInfo ci) {
        this.updateValues();
    }
*/
    @Inject(method = "update", at = @At("HEAD"))
    public void modBeforeUpdate(PlayerEntity player, CallbackInfo ci) {
        Difficulty difficulty = player.getWorld().getDifficulty();

        boolean naturalRegenerative = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        this.prevFoodLevel = (int) this.foodLevel;

        if (!player.isCreative()) {
            if (player.getHealth() < player.getMaxHealth()) {
                this.exhaustion += Math.max(0.0F, 0.002F + (this.foodLevel > 300 ? 0.002F : 0.0F));
            }

            if (this.exhaustion > 1.5F) {
                this.exhaustion -= 1.5F;
                if (this.saturationLevel > 0.0F) {
                    this.saturationLevel = (Math.max(this.saturationLevel - 0.4F, 0.0F));
                    if (this.saturationLevel > 0.0F && this.foodLevel > 200) {
                        this.saturationLevel = (Math.max(this.saturationLevel - 0.4F, 0.0F));
                    }
                }
                if (this.saturationLevel <= 0.0F) {
                    this.foodLevel = (Math.max(this.foodLevel - 2, 0));
                } else if (this.saturationLevel < 3.0F) {
                    this.foodLevel = (Math.max(this.foodLevel - 1, 0));
                }
            }


            if (this.foodLevel >= 350) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false));

                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (80)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0, false, false));
                    this.exhaustion = Math.max(0.0F, this.exhaustion + 3.9F);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(DamageTypeRegistry.getSource(player.getWorld(), DamageTypeRegistry.OVEREATING_DAMAGE_TYPE), 1.0F);
                        }
                    }

                    this.foodTickTimer = 0;
                }
            } else if (this.foodLevel >= 310) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0, false, false));

                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (100)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 0, false, false));
                    this.exhaustion = Math.max(0.0F, this.exhaustion + 2.5F);

                    this.foodTickTimer = 0;
                }
            } else if (naturalRegenerative && this.saturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= 120 && this.foodLevel < 310) {
                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (10)) {
                    float f = Math.min(this.saturationLevel, 5.0F);
                    player.heal(f / 5.0F);
                    this.exhaustion = Math.max(0.0F, this.exhaustion + (f / 2.0F));

                    this.foodTickTimer = 0;
                }
            } else if (naturalRegenerative && this.saturationLevel <= 0.0F && player.canFoodHeal() && this.foodLevel >= 100 && player.getHealth() <= player.getMaxHealth() / 1.15) {
                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (3)) {
                    this.foodLevel -= 10;
                    this.saturationLevel += 3.5F;
                    this.exhaustion = Math.max(0.0F, this.exhaustion + 0.2F);

                    this.foodTickTimer = 0;
                }
            } else if (naturalRegenerative && player.canFoodHeal() && this.foodLevel >= 80 && this.foodLevel < 120) {
                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (55)) {
                    player.heal(1);
                    this.exhaustion = Math.max(0.0F, this.exhaustion + 2.3F);

                    this.foodTickTimer = 0;
                }
            } else if (this.foodLevel <= 80 && this.foodLevel > 19) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0, false, false));
            } else if (this.foodLevel <= 19) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false));

                this.foodTickTimer += 1;

                if (this.foodTickTimer >= (40 + this.foodLevel * 2)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 0, false, false));
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(player.getDamageSources().starve(), 1.0F);
                        }
                    }

                    this.foodTickTimer = 0;
                }
            }
        }
        //this.updateValues();
    }
}
