/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.felixagairu.saturative_overhaul.Saturative.LOGGER;
/*? >=1.21.2 {*/
/*
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
*//*?} else >=1.21.1 {*/
import net.minecraft.world.World;
/*?}*/


public class DamageHelper {
    private static boolean HAS_ERROR = false;

    public static DamageSource damageSourceFromString(
            /*? >=1.21.2 {*//*ServerWorld world,*//*?} else >=1.21.1 {*/World world,/*?}*/
            String inId,
            String defaultId
    ) {
        Identifier id = Identifier.of(inId.toLowerCase());
        RegistryKey<DamageType> key = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id);

        Identifier idDefault = Identifier.of(inId.toLowerCase());
        RegistryKey<DamageType> defaultKey = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, idDefault);

        /*? >=1.21.2 {*/
        /*// RegistryEntryLookup<DamageType> lookup
        var lookup = world.getRegistryManager()
                .getOrThrow(RegistryKeys.DAMAGE_TYPE);

        // RegistryEntry<DamageType> entry
        var entry = lookup.getOptional(key).orElse(null);

        if (entry == null) {
            if (!HAS_ERROR) {
                LogHelper.error(Text.translatable(
                        "util.DamageHelper.FallbackToDefault",
                        inId.toLowerCase(),
                        inId.toLowerCase(),
                        defaultId.toLowerCase()
                ).getString());

                HAS_ERROR = true;
            }
            entry = lookup.getOptional(defaultKey).orElse(lookup.getOptional(DamageTypes.STARVE).get());
        }
        return new DamageSource(entry);
        *//*?} else >=1.21.1 {*/
        // Registry<DamageType> registry
        var registry = world.getRegistryManager()
                .get(RegistryKeys.DAMAGE_TYPE);

        if (registry == null) {
            throw new IllegalStateException("DamageType registry missing");
        }

        // Optional<RegistryEntry<DamageType>> entry
        var entry = registry.getEntry(key).orElse(null);
        if (entry == null) {
            if (!HAS_ERROR) {
                LogHelper.error(Text.translatable(
                        "util.DamageHelper.FallbackToDefault",
                        inId.toLowerCase(),
                        inId.toLowerCase(),
                        defaultId.toLowerCase()
                ).getString());

                HAS_ERROR = true;
            }
            entry = registry.getEntry(defaultKey).orElse(registry.getEntry(DamageTypes.STARVE).get());
        }

        return new DamageSource(entry);
        /*?} else {*/
        /*?}*/
    }

    public static void doEffectDamage(
            /*? >=1.21.2 {*//*ServerWorld world,*//*?} else >=1.21.1 {*/World world,/*?}*/
            /*? >=1.21.2 {*//*ServerPlayerEntity player,*//*?} else >=1.21.1 {*/PlayerEntity player,/*?}*/
            String inId,
            float amount,
            String defaultId
    ) {
        /*? >=1.21.2 {*/
        /*player.damage(
                WorldPlayerUtils.getWorld(player),
                damageSourceFromString(world, inId, defaultId),
                amount
        );
        *//*?} else >=1.21.1 {*/
        player.damage(
                damageSourceFromString(world, inId, defaultId),
                amount
        );
        /*?} else {*/
        /*?}*/

    }
    public static void doRealDamage(PlayerEntity player, float amount) {
        player.setHealth(player.getHealth() - amount);
    }
}
