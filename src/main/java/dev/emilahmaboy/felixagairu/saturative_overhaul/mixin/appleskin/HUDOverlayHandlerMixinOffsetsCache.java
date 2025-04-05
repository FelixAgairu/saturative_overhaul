package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin.appleskin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues.foodBarDelta;
import static dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues.foodBarSize;


@Pseudo
@Mixin(targets = "squeek.appleskin.client.HUDOverlayHandler$OffsetsCache")
public class HUDOverlayHandlerMixinOffsetsCache {
    @ModifyConstant(
            method = "generate",
            constant = @Constant(
                    intValue = 10,
                    ordinal = 4
            )
    )
    private int modifyFoodCount(int ignored) {
        return foodBarSize;
    }

    @ModifyConstant(
            method = "generate",
            constant = @Constant(
                    intValue = 8,
                    ordinal = 1
            )
    )
    private int modifyFoodDelta(int ignored) {
        return foodBarDelta;
    }

    @Inject(
            method = "generate",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/player/HungerManager;getFoodLevel()I",
                            ordinal = 0
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;ceil(D)D",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void modifyShouldAnimatedFood(int guiTicks, PlayerEntity player, CallbackInfo ci, @Local(ordinal = 1) LocalBooleanRef shouldAnimatedFood) {
        HungerManager hungerManager = player.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();
        float saturationLevel = hungerManager.getSaturationLevel();

        boolean isFoodBarShouldBeAnimated = HungerManagerValues.of(foodLevel, saturationLevel).isFoodBarShouldBeAnimated(guiTicks);
        if (isFoodBarShouldBeAnimated) {
            shouldAnimatedFood.set(true);
        }
    }
}