/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

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
    private static boolean HAS_ERROR = false;

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

            if (!HAS_ERROR) {
                LOGGER.error(Text.translatable(
                        "util.EffectHelper.FallbackToDefault",
                        inId.toLowerCase(),
                        inId.toLowerCase(),
                        defaultId.toLowerCase()
                ).getString());

                HAS_ERROR = true;
            }
        }
    }
}
