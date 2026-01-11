package dev.felixagairu.saturative_overhaul.mixin;

import dev.felixagairu.saturative_overhaul.util.ConfigHelper;
import dev.felixagairu.saturative_overhaul.util.LogHelper;
import dev.felixagairu.saturative_overhaul.util.WorldPlayerUtils;
import dev.felixagairu.saturative_overhaul.util.MathHelper;
import net.minecraft.entity.player.HungerManager;

/*? 1.21.1 {*/
import net.minecraft.entity.player.PlayerEntity;
/*?}*/

import net.minecraft.server.network.ServerPlayerEntity;

/*? >=1.21.11 {*/
/*import net.minecraft.world.rule.GameRules;
*//*?}*/


import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow
    @Final
    private static Logger LOGGER;
    @Unique
    private int tickCounterFoodLevel = 0;
    @Unique
    private int tickIntervalFoodLevel = 20;
    @Unique
    private int tickCounterExhaustion = 0;
    @Unique
    private int tickIntervalExhaustion = 20;
    @Unique
    private int newFoodLevel = 0;
    @Unique
    private float newExhaustion = 0.0f;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        /*? <=1.21.1 {*/
        PlayerEntity player = (PlayerEntity)(Object)this;
        /*?} else <=1.21.11 {*/
        /*ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        *//*?} else {*//*
        *//*?}*/
        HungerManager hungerManager = player.getHungerManager();

        boolean check =
                !WorldPlayerUtils.isCreative(player)
             && !WorldPlayerUtils.isPeaceful(player)
             && WorldPlayerUtils.isNaturalRegenerative(player);

        if (check) {
            if (ConfigHelper.decreaseFoodLevelOverTimeEnabled) {
                tickCounterFoodLevel += 1;

                if (tickCounterFoodLevel >= tickIntervalFoodLevel && hungerManager.getFoodLevel() > 1) {
                    // Random ticks or not
                    if (ConfigHelper.randomDecreaseFoodLevelEnabled) {
                        // Get next random ticks
                        tickIntervalFoodLevel = (int) MathHelper.generateRandom(
                            (float) ConfigHelper.decreaseFoodLevelBaseTicks,
                            ConfigHelper.randomDecreaseFoodLevelMinMultiplier,
                            ConfigHelper.randomDecreaseFoodLevelMaxMultiplier
                        );

                        tickCounterFoodLevel = 0;
                    } else {
                        // Next fixed ticks
                        tickIntervalFoodLevel = ConfigHelper.decreaseFoodLevelBaseTicks;
                    }
                    // Random food level or not
                    //int newFoodLevel
                    if (ConfigHelper.decreaseRandomFoodLevelEnabled) {
                        // Get random food level
                        newFoodLevel = (int) MathHelper.clampFoodLevel(MathHelper.generateRandom(
                                ConfigHelper.decreaseFoodLevelBaseAmounts,
                                ConfigHelper.decreaseFoodLevelMinMultiplier,
                                ConfigHelper.decreaseFoodLevelMaxMultiplier
                        ));
                    } else {
                        // Get fixed food level
                        newFoodLevel = (int) MathHelper.clampFoodLevel(ConfigHelper.decreaseFoodLevelBaseAmounts);
                    }
                    // Set food level
                    hungerManager.setFoodLevel(hungerManager.getFoodLevel() - newFoodLevel);
                }
            }
            // Add exhaustion or not
            if (ConfigHelper.addExhaustionOverTimeEnabled) {
                tickCounterExhaustion += 1;

                if (tickCounterExhaustion >= tickIntervalExhaustion) {
                    // Random ticks or not
                    if (ConfigHelper.randomAddExhaustionEnabled) {
                        // Get next random ticks
                        tickIntervalExhaustion = (int) MathHelper.generateRandom(
                                (float) ConfigHelper.addExhaustionBaseTicks,
                                ConfigHelper.randomAddExhaustionMinMultiplier,
                                ConfigHelper.randomAddExhaustionMaxMultiplier
                        );

                        tickCounterExhaustion = 0;
                    } else {
                        // Next fixed ticks
                        tickIntervalExhaustion = ConfigHelper.addExhaustionBaseTicks;
                    }
                    // Random exhaustion or not
                    //float newExhaustion;
                    if (ConfigHelper.addRandomExhaustionEnabled) {
                        // Get random exhaustion
                        newExhaustion = MathHelper.generateRandom(
                                ConfigHelper.addExhaustionBaseAmounts,
                                ConfigHelper.addExhaustionMinMultiplier,
                                ConfigHelper.addExhaustionMaxMultiplier
                        );
                    } else {
                        // Get fixed exhaustion
                        newExhaustion = ConfigHelper.addExhaustionBaseAmounts;
                    }
                    // Set exhaustion
                    hungerManager.addExhaustion(newExhaustion);
                }
            }

            LogHelper.info("==================================================");
            LogHelper.info("tickIntervalFoodLevel: {}", tickIntervalFoodLevel);
            LogHelper.info("tickIntervalExhaustion:  {}", tickIntervalExhaustion);
            LogHelper.info("newFoodLevel: {}", newFoodLevel);
            LogHelper.info("newExhaustion: {}", newExhaustion);
            LogHelper.info("==================================================");
        }
    }
}
