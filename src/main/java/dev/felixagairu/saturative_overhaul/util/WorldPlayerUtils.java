/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

/*? >=1.21.11 {*/
/*import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.rule.GameRules;

public class WorldPlayerUtils {
    public static ServerWorld getWorld(ServerPlayerEntity player) {
        return player.getEntityWorld();
    }
    public static Difficulty getDifficulty(ServerPlayerEntity player) {
        return getWorld(player).getDifficulty();
    }
    public static boolean isPeaceful(ServerPlayerEntity player) {
        return getDifficulty(player) == Difficulty.PEACEFUL;
    }
    public static boolean isCreative(ServerPlayerEntity player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }
    public static boolean isNaturalRegenerative(ServerPlayerEntity player) {
        return getWorld(player).getGameRules().getValue(GameRules.NATURAL_HEALTH_REGENERATION);
    }
}
*//*?} else >=1.21.9 {*/
/*import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;

public class WorldPlayerUtils {
    public static ServerWorld getWorld(ServerPlayerEntity player) {
        return player.getEntityWorld();
    }
    public static Difficulty getDifficulty(ServerPlayerEntity player) {
        return getWorld(player).getDifficulty();
    }
    public static boolean isPeaceful(ServerPlayerEntity player) {
        return getDifficulty(player) == Difficulty.PEACEFUL;
    }
    public static boolean isCreative(ServerPlayerEntity player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }
    public static boolean isNaturalRegenerative(ServerPlayerEntity player) {
        return getWorld(player).getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
    }
}
*//*?} else >=1.21.6 {*/
/*import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;

public class WorldPlayerUtils {
    public static ServerWorld getWorld(ServerPlayerEntity player) {
        return player.getWorld();
    }
    public static Difficulty getDifficulty(ServerPlayerEntity player) {
        return getWorld(player).getDifficulty();
    }
    public static boolean isPeaceful(ServerPlayerEntity player) {
        return getDifficulty(player) == Difficulty.PEACEFUL;
    }
    public static boolean isCreative(ServerPlayerEntity player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }
    public static boolean isNaturalRegenerative(ServerPlayerEntity player) {
        return getWorld(player).getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
    }
}
*//*?} else >=1.21.2 {*/
/*import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class WorldPlayerUtils {
    public static ServerWorld getWorld(ServerPlayerEntity player) {
        return player.getServerWorld();
    }
    public static Difficulty getDifficulty(ServerPlayerEntity player) {
        return getWorld(player).getDifficulty();
    }
    public static boolean isPeaceful(ServerPlayerEntity player) {
        return getDifficulty(player) == Difficulty.PEACEFUL;
    }
    public static boolean isCreative(ServerPlayerEntity player) {
        return player.isCreative();
    }
    public static boolean isNaturalRegenerative(ServerPlayerEntity player) {
        return getWorld(player).getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
    }
}
*//*?} else >=1.21.1 {*/
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class WorldPlayerUtils {
    public static World getWorld(PlayerEntity player) {
        return player.getWorld();
    }
    public static Difficulty getDifficulty(PlayerEntity player) {
        return player.getWorld().getDifficulty();
    }
    public static boolean isPeaceful(PlayerEntity player) {
        return player.getWorld().getDifficulty() == Difficulty.PEACEFUL;
    }
    public static boolean isCreative(PlayerEntity player) {
        return player.isCreative();
    }
    public static boolean isNaturalRegenerative(PlayerEntity player) {
        return player.getWorld().getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
    }
}
/*?} else {*//*
*//*?}*/