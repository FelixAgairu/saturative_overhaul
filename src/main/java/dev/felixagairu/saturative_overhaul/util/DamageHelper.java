package dev.felixagairu.saturative_overhaul.util;

import dev.felixagairu.saturative_overhaul.registries.DamageTypeRegistry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
/*? <=1.21.1 {*/
/*?} else {*/
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
/*?}*/

public class DamageHelper {
    /*? <=1.21.1 {*/
    /*public static void doEffectDamage(PlayerEntity player, float amount, RegistryKey<DamageType> DAMAGE_TYPE) {
    *//*?} else {*/
    public static void doEffectDamage(ServerPlayerEntity player, float amount, RegistryKey<DamageType> DAMAGE_TYPE) {
    /*?}*/

        /*? <=1.21.1 {*/
            /*player.damage(DamageTypeRegistry.getSource(player.getWorld(), DAMAGE_TYPE), amount);
        *//*?} else <=1.21.5 {*/
            /*player.damage(player.getServerWorld(), DamageTypeRegistry.getSource(player, DAMAGE_TYPE), amount);
        *//*?} else <=1.21.11 {*/
            player.damage(player.getEntityWorld(), DamageTypeRegistry.getSource(player, DAMAGE_TYPE), amount);
        /*?}*/
    }
    public static void doRealDamage(PlayerEntity player, float amount) {
        player.setHealth(player.getHealth() - amount);
    }
}
