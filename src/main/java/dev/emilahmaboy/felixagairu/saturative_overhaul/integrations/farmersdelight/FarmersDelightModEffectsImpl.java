package dev.emilahmaboy.felixagairu.saturative_overhaul.integrations.farmersdelight;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import vectorwing.farmersdelight.common.registry.ModEffects;


public class FarmersDelightModEffectsImpl implements FarmersDelightModEffectsService {
    @Override
    public boolean hasNourishmentEffect(PlayerEntity playerEntity) {
        /*Identifier id = Identifier.of("farmersdelight:nourishment");
        StatusEffect statusEffect = (NourishmentEffect) Registries.STATUS_EFFECT.get(id);

        return statusEffect != null && playerEntity.hasStatusEffect((RegistryEntry) statusEffect);*/

        RegistryEntry<StatusEffect> registryEntry = Registries.STATUS_EFFECT.getEntry(ModEffects.NOURISHMENT.value());
        return playerEntity.hasStatusEffect(registryEntry);

    }
}
