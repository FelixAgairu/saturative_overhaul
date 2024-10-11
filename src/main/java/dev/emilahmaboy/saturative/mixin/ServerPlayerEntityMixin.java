package dev.emilahmaboy.saturative.mixin;

import dev.emilahmaboy.saturative.api.Helper;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
/*
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void nbtSaveCustom(NbtCompound nbt, CallbackInfo ci) {
        Helper.saveNbt(nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void nbtLoadCustom(NbtCompound nbt, CallbackInfo ci) {
        Helper.loadNbt(nbt);
    }*/
}