package dev.felixagairu.saturative_overhaul.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.felixagairu.saturative_overhaul.Saturative.LOGGER;

public class EffectHelper {
    public static void gainEffect(
            PlayerEntity player,
            String inId,
            int duration,
            int amplification,
            String defaultId
    ) {
        Identifier id = Identifier.of(inId.toLowerCase());
        RegistryEntry<StatusEffect> effectEntry = Registries.STATUS_EFFECT.getEntry(id).orElse(null);

        if (!(effectEntry == null)) {
            player.addStatusEffect(new StatusEffectInstance(effectEntry, duration, amplification, false, false));
        } else {
            Identifier fallBackId = Identifier.of(defaultId.toLowerCase());
            RegistryEntry<StatusEffect> fallBackEffect = Registries.STATUS_EFFECT.getEntry(fallBackId).orElse(null);

            player.addStatusEffect(new StatusEffectInstance(fallBackEffect, duration, amplification, false, false));

            LOGGER.error(Text.translatable(
                    "util.EffectHelper.FallbackToDefault",
                    inId.toLowerCase(),
                    defaultId.toLowerCase()
            ).getString());
        }
    }
}
