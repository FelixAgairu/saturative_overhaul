package dev.emilahmaboy.felixagairu.saturative_overhaul.integrations.farmersdelight;

import net.minecraft.entity.player.PlayerEntity;

public class FarmersDelightModEffectsImplFallback implements FarmersDelightModEffectsService {
    @Override
    public boolean hasNourishmentEffect(PlayerEntity playerEntity) {
        return false;
    }
}
