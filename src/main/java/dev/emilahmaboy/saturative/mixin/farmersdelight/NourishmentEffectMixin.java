package dev.emilahmaboy.saturative.mixin.farmersdelight;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;


@Pseudo
@Mixin(targets = "vectorwing.farmersdelight.common.effect.NourishmentEffect")
public class NourishmentEffectMixin extends StatusEffect {
    protected NourishmentEffectMixin(StatusEffectCategory category, int color) {
        super(category, color);
    }

    /**
     * @author EmilAhmaBoy
     * @reason Saturative mod needs for some changes in NourishmentEffect
     */
    @Overwrite
    public boolean
    applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getEntityWorld().isClient && entity instanceof PlayerEntity player) {
            HungerManager foodData = player.getHungerManager();
            boolean isSaturating = true;
            if (foodData.getFoodLevel() < 280) {
                if (foodData.getFoodLevel() > 120 && (foodData.getSaturationLevel() <= 1.0F || foodData.getSaturationLevel() > 3.0F)) {
                    // not saturating
                    isSaturating = false;
                } else {
                    // saturating
                    isSaturating = true;
                }
            } else {
                // not saturating
                isSaturating = false;
            }

            if (isSaturating) {
                if (foodData.getExhaustion() >= 0.3F - 0.1F * (float) amplifier) {
                    player.addExhaustion(-0.3F - 0.1F * (float) amplifier);
                }
            }
        }
        return true;
    }
}
