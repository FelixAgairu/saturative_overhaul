package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;


@Pseudo
@Mixin(targets = "vectorwing.farmersdelight.client.gui.NourishmentHungerOverlay")
public class NourishmentHungerOverlayMixin {
    @ModifyConstant(
            method = "drawNourishmentOverlay",
            constant = @Constant(
                    intValue = 10,
                    ordinal = 0
            )
    )
    private static int dontDrawNourishmentOverlay(int constant) {
        return 0;
    }
}
