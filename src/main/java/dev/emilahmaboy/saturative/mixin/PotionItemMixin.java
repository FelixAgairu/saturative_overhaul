package dev.emilahmaboy.saturative.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {
    @Inject(
            method = "finishUsing",
            at = @At(
                    value = "HEAD"
            )
    )
    private void decreaseRedundantFood(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof PlayerEntity player) {
            float exhaustion = 6.0F;
            int duration = 100;
            int amplifier = 1;
            
			PotionContentsComponent potionContentsComponent = (PotionContentsComponent) stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            for (StatusEffectInstance effect : potionContentsComponent.getEffects()) {
                duration = getDuration(effect, duration);
                amplifier = getAmplifier(effect, amplifier);
                exhaustion = getExhaustion(effect, exhaustion);
            }

            HungerManager hungerManager = player.getHungerManager();
            int foodLevel = hungerManager.getFoodLevel();
            if (foodLevel >= 300) {
                hungerManager.addExhaustion(exhaustion);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, duration, amplifier, true, false, false));
            }
        }
    }

    @Unique
    private int getDuration(StatusEffectInstance statusEffectInstance, int duration) {
        if (statusEffectInstance.getEffectType() == StatusEffects.SLOWNESS) {
            duration *= 3;
        } else if (statusEffectInstance.getEffectType() == StatusEffects.SPEED) {
            duration = duration / 3;
        } else {
            duration = (int) (duration / 1.5);
        }
        return duration;
    }

    @Unique
    private int getAmplifier(StatusEffectInstance statusEffectInstance, int amplifier) {
        if (statusEffectInstance.getEffectType() == StatusEffects.SLOWNESS) {
            amplifier = 0;
        } else if (statusEffectInstance.getEffectType() == StatusEffects.SPEED) {
            amplifier = 4;
        }
        return amplifier;
    }

    @Unique
    private float getExhaustion(StatusEffectInstance statusEffectInstance, float exhaustion) {
        if (statusEffectInstance.getEffectType() == StatusEffects.SLOWNESS) {
            exhaustion = 1.0F;
        } else if (statusEffectInstance.getEffectType() == StatusEffects.SPEED) {
            exhaustion = 7.0F;
        }
        return exhaustion;
    }
}
