package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin.appleskin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import squeek.appleskin.ModConfig;
import squeek.appleskin.helpers.FoodHelper;

import static dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues.maxFoodLevel;
import static dev.emilahmaboy.felixagairu.saturative_overhaul.tools.HungerManagerValues.nutritionModifier;


@Pseudo
@Mixin(targets = "squeek.appleskin.client.HUDOverlayHandler")
public abstract class HUDOverlayHandlerMixin {
    @ModifyExpressionValue(
            method = "drawSaturationOverlay(Lnet/minecraft/client/gui/DrawContext;FFLnet/minecraft/client/MinecraftClient;IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(FF)F",
                    ordinal = 0
            )
    )
    private float drawSaturationOverlay(float original, DrawContext context, float saturationGained, float saturationLevel, MinecraftClient mc, int right, int top, float alpha) {
        PlayerEntity player = mc.player;
        if (player != null) {
            int foodLevel = player.getHungerManager().getFoodLevel();
            ItemStack heldItem = player.getMainHandStack();
            if (ModConfig.INSTANCE.showFoodValuesHudOverlayWhenOffhand &&
                    heldItem.get(DataComponentTypes.FOOD) != null ? !FoodHelper.canConsume(player, (FoodComponent) heldItem.get(DataComponentTypes.FOOD)) : true
            ) {
                heldItem = player.getOffHandStack();
            }

            FoodComponent foodComponent = (FoodComponent) heldItem.get(DataComponentTypes.FOOD);
            int modifiedFoodValue = foodComponent != null ? foodComponent.nutrition() : 0;

            int modifiedFood = modifiedFoodValue * nutritionModifier;
            int newFoodLevel = Math.min(foodLevel + modifiedFood, maxFoodLevel);

            return HungerManagerValues.of(newFoodLevel, original).trimSaturationLevel().getSaturationLevel();
        }
        return original;
    }


    @ModifyConstant(
            method = "drawExhaustionOverlay*",
            constant = @Constant(
                    floatValue = 81.0F,
                    ordinal = 0
            )
    )
    private float dontDrawExhaustionOverlay(float ignored) {
        return 0.0F;
    }

    @ModifyExpressionValue(
            method = "drawHungerOverlay(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/MinecraftClient;IIFZ)V",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;ceil(D)D", ordinal = 0)
    )
    private double dontDrawHungerOverlay(double original) {
        return 0;
    }
}
