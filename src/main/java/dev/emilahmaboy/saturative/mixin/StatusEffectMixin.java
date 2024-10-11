package dev.emilahmaboy.saturative.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net.minecraft.entity.effect.HungerStatusEffect")
public class StatusEffectMixin {
    @ModifyConstant(
            method = "applyUpdateEffect",
            constant = @Constant(
                    floatValue = 0.005F,
                    ordinal = 0
            )
    )
    public float moreExhaustion(float constant) {
        return 0.03F;
    }
}