/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
// This file includes modifications by Felix.
// Licensed under the MPL 2.0. Original portions © saturative upstream under MIT by EmilAhmaBoy.

package dev.felixagairu.saturative_overhaul.registries;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
/*? <1.21.2 {*/
/*import net.minecraft.world.World;
*//*?} else {*/
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
/*?}*/
import net.minecraft.util.Identifier;


public class DamageTypeRegistry {
    public static final RegistryKey<DamageType> OVEREATING_DAMAGE_TYPE =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("saturative_overhaul", "overeating"));

    public static final RegistryKey<DamageType> STARVING_DAMAGE_TYPE =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("saturative_overhaul", "starving"));

    /*? <1.21.2 {*/
    /*public static DamageSource getSource(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
    *//*?} else {*/
    public static DamageSource getSource(ServerPlayerEntity player, RegistryKey<DamageType> key) {
        return player.getDamageSources().create(key);
    }
    /*?}*/
}
