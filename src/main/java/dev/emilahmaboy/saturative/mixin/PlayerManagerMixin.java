package dev.emilahmaboy.saturative.mixin;

import dev.emilahmaboy.saturative.api.Helper;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

/*
    @Inject(method = "savePlayerData", at = @At("TAIL"))
    private void nbtSavePlayerData(ServerPlayerEntity player, CallbackInfo ci) {
        NbtCompound nbt = new NbtCompound();
        player.writeCustomDataToNbt(nbt);
        Helper.saveNbt(nbt); // Save hunger data
    }

    @Inject(method = "loadPlayerData", at = @At("TAIL"))
    private void nbtLoadPlayerData(ServerPlayerEntity player, CallbackInfoReturnable<Optional<NbtCompound>> cir) {
        if (cir.getReturnValue().isPresent()) {
            NbtCompound nbt = cir.getReturnValue().get();
            Helper.loadNbt(nbt); // Load hunger data
        }
    }*/
}


