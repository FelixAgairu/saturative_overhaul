package dev.felixagairu.saturative_overhaul.mixin;

import dev.felixagairu.saturative_overhaul.util.ConfigData;
import dev.felixagairu.saturative_overhaul.util.LimitRandomizer;
import net.minecraft.entity.player.HungerManager;

/*? <1.21.2 {*/
/*import net.minecraft.entity.player.PlayerEntity;
*//*?}*/

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;

/*? <1.21.11 {*/
import net.minecraft.world.GameRules;
/*?} else {*/
/*import net.minecraft.world.rule.GameRules;
*//*?}*/

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow
    public abstract ServerWorld getServerWorld();

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    public abstract void playerTick();

    @Unique
    private int tickCounterA = 1;
    @Unique
    private float theInterval = 20;

    @Unique
    private boolean isPeaceful() {
        ServerWorld serverWorld = this.getServerWorld();
        return serverWorld.getDifficulty() == Difficulty.PEACEFUL;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        /*? <=1.21.1 {*/
        /*PlayerEntity player = (PlayerEntity)(Object)this;

        boolean naturalRegenerative = player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        *//*?} else <=1.21.11 {*/
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        ServerWorld serverWorld = this.getServerWorld();

        boolean naturalRegenerative = serverWorld.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        /*?} else {*//*
        *//*?}*/
        HungerManager hungerManager = player.getHungerManager();

        boolean check =
                ConfigData.decreaseFoodLevelOverTimeEnabled
             && !this.isCreative()
             && !this.isPeaceful()
             && naturalRegenerative;
        if (check) {
            tickCounterA += 1;

            if (ConfigData.randomDecreaseFoodLevelEnabled) {
                if (tickCounterA % theInterval == 0) {
                    theInterval = (int) LimitRandomizer.generateRandom(
                            (float) ConfigData.randomDecreaseFoodLevelBaseTicks,
                                    ConfigData.randomDecreaseFoodLevelMinMultiplier,
                                    ConfigData.randomDecreaseFoodLevelMaxMultiplier);
                    // Safeguard
                    theInterval = Math.clamp(theInterval, 1, 65535);
                    int foodValueStorage = hungerManager.getFoodLevel();
                    if (foodValueStorage > 1) {
                        hungerManager.setFoodLevel(Math.clamp(foodValueStorage - ConfigData.randomDecreaseFoodLevelPerTickAmounts, 0, ConfigData.maxFoodLevel));
                        hungerManager.addExhaustion(ConfigData.randomAddExhaustionPerTickAmounts);
                    }
                    this.tickCounterA = 1;
                }
            } else {
                if (tickCounterA > theInterval) {
                    theInterval = ConfigData.randomDecreaseFoodLevelBaseTicks;
                    this.tickCounterA = 1;
                }
            }

        }
    }
}
