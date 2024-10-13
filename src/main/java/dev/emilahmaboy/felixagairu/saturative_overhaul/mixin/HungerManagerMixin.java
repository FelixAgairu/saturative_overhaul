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
    @Unique
    private double modFoodLevel = Helper.foodLevel;
    @Unique
    private float modSaturationLevel = Helper.foodSaturationLevel;
    @Unique
    private float modExhaustion = Helper.foodExhaustionLevel;
    @Unique
    private int modFoodTickTimer = 0;


    @Unique
    private void updateValues() {
        this.foodLevel = Helper.foodLevel = (int) this.modFoodLevel;
        this.foodTickTimer = Helper.foodTickTimer = this.modFoodTickTimer;
        this.saturationLevel = Helper.foodSaturationLevel = this.modSaturationLevel;
        this.exhaustion = Helper.foodExhaustionLevel =this.modExhaustion;
        this.prevFoodLevel = (int) this.modFoodLevel;
    }

    /**
     * @return
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I"))
    private int foodLevelChange(int nutritionAdded, int min, int max) {
        double nutrition = nutritionAdded - this.modFoodLevel;
        double multiNutrition = nutrition * Helper.nutritionModifier;
        double randomizedMultiNutrition = TheRandom.dTheRandom(multiNutrition, 0.888D, 1.222D);
        this.modFoodLevel = MathHelper.clamp(randomizedMultiNutrition + this.modFoodLevel, 0, this.maxFoodLevel);
        return (int) this.modFoodLevel;
    }

    @Redirect(method = "addInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float saturationLevelChange(float saturationAdded, float min, float max) {
        double randomizedSaturationAdded = TheRandom.dTheRandom(saturationAdded, 0.8D, 1.2D);
        this.modSaturationLevel = MathHelper.clamp((float) randomizedSaturationAdded, 0.0F, (float) this.modFoodLevel / 18.0F);
        return this.modSaturationLevel;
    }

    @ModifyReturnValue(method = "isNotFull", at = @At("TAIL"))
    public boolean isNotFull(boolean original) {
        return this.modFoodLevel < maxFoodLevel;
    }

    /**
     * @author EmilAhmaBoy
     * @reason Saturative mod overwrites HungerManager
     */

    @Inject(method = "update", at = @At("TAIL"))
    public void modAfterUpdate(PlayerEntity player, CallbackInfo ci) {
        this.updateValues();
    }

    @Inject(method = "update", at = @At("HEAD"))
    public void modBeforeUpdate(PlayerEntity player, CallbackInfo ci) {
        Difficulty difficulty = player.getWorld().getDifficulty();

        boolean naturalRegenerative = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        this.prevFoodLevel = (int) this.modFoodLevel;

        if (!player.isCreative()) {
            if (player.getHealth() < player.getMaxHealth()) {
                this.modExhaustion += Math.max(0.0F, 0.002F + (this.modFoodLevel > 300 ? 0.002F : 0.0F));
            }

            if (this.modExhaustion > 1.5F) {
                this.modExhaustion -= 1.5F;
                if (this.modSaturationLevel > 0.0F) {
                    this.modSaturationLevel = (Math.max(this.modSaturationLevel - 0.4F, 0.0F));
                    if (this.modSaturationLevel > 0.0F && this.modFoodLevel > 200) {
                        this.modSaturationLevel = (Math.max(this.modSaturationLevel - 0.4F, 0.0F));
                    }
                }
                if (this.modSaturationLevel <= 0.0F) {
                    this.modFoodLevel = (Math.max(this.modFoodLevel - 2, 0));
                } else if (this.modSaturationLevel < 3.0F) {
                    this.modFoodLevel = (Math.max(this.modFoodLevel - 1, 0));
                }
            }


            if (this.modFoodLevel >= 350) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false));

                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (80)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0, false, false));
                    this.modExhaustion = Math.max(0.0F, this.modExhaustion + 3.9F);
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(DamageTypeRegistry.getSource(player.getWorld(), DamageTypeRegistry.OVEREATING_DAMAGE_TYPE), 1.0F);
                        }
                    }

                    this.modFoodTickTimer = 0;
                }
            } else if (this.modFoodLevel >= 310) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0, false, false));

                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (100)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 0, false, false));
                    this.modExhaustion = Math.max(0.0F, this.modExhaustion + 2.5F);

                    this.modFoodTickTimer = 0;
                }
            } else if (naturalRegenerative && this.modSaturationLevel > 0.0F && player.canFoodHeal() && this.modFoodLevel >= 120 && this.modFoodLevel < 310) {
                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (10)) {
                    float f = Math.min(this.modSaturationLevel, 5.0F);
                    player.heal(f / 5.0F);
                    this.modExhaustion = Math.max(0.0F, this.modExhaustion + (f / 2.0F));

                    this.modFoodTickTimer = 0;
                }
            } else if (naturalRegenerative && this.modSaturationLevel <= 0.0F && player.canFoodHeal() && this.modFoodLevel >= 100 && player.getHealth() <= player.getMaxHealth() / 1.15) {
                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (3)) {
                    this.modFoodLevel -= 10;
                    this.modSaturationLevel += 3.5F;
                    this.modExhaustion = Math.max(0.0F, this.modExhaustion + 0.2F);

                    this.modFoodTickTimer = 0;
                }
            } else if (naturalRegenerative && player.canFoodHeal() && this.modFoodLevel >= 80 && this.modFoodLevel < 120) {
                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (55)) {
                    player.heal(1);
                    this.modExhaustion = Math.max(0.0F, this.modExhaustion + 2.3F);

                    this.modFoodTickTimer = 0;
                }
            } else if (this.modFoodLevel <= 80 && this.modFoodLevel > 40) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0, false, false));
            } else if (this.modFoodLevel <= 40) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1, false, false));

                this.modFoodTickTimer += 1;

                if (this.modFoodTickTimer >= (40 + this.modFoodLevel * 2)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 0, false, false));
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        if (difficulty != Difficulty.PEACEFUL) {
                            player.damage(player.getDamageSources().starve(), 1.0F);
                        }
                    }

                    this.modFoodTickTimer = 0;
                }
            }
        }
        this.updateValues();
    }
}
