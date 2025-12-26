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
import dev.felixagairu.saturative_overhaul.tools.LimitRandomizer;
import dev.felixagairu.saturative_overhaul.tools.NbtUpdater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    @Unique
    private int tickCounterA = 1;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract HungerManager getHungerManager();

    @Shadow
    public abstract boolean isCreative();

    @ModifyReturnValue(
            method = "canConsume",
            at = @At("TAIL")
    )
    private boolean canConsume(boolean original, boolean ignoreHunger) {
        return original;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void nbtSaveCustom(NbtCompound nbt, CallbackInfo ci) {
        NbtUpdater.saveNbt(nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void nbtLoadCustom(NbtCompound nbt, CallbackInfo ci) {
        NbtUpdater.loadNbt(nbt);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        if (!this.isCreative() && this.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
            tickCounterA += 1;
            if (tickCounterA % (int) LimitRandomizer.fTheRandom(100F, (9F / 10F), (7F / 3F)) == 0) {
                int foodValueStorage = this.getHungerManager().getFoodLevel();
                if (foodValueStorage > 1) {
                    this.getHungerManager().setFoodLevel(foodValueStorage - 1);
                    this.getHungerManager().addExhaustion(0.02F);
                }
                this.tickCounterA = 1;
            }
        }
    }
}
