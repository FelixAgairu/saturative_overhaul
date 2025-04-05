package dev.emilahmaboy.felixagairu.saturative_overhaul.tools;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class NbtUpdater {
    public static int foodLevel;
    public static int foodTickTimer;
    public static float foodSaturationLevel;
    public static float foodExhaustionLevel;


    public static void saveNbt(NbtCompound nbt) {
        nbt.putInt("foodLevel", NbtUpdater.foodLevel);
        nbt.putInt("foodTickTimer", NbtUpdater.foodTickTimer);
        nbt.putFloat("foodSaturationLevel", NbtUpdater.foodSaturationLevel);
        nbt.putFloat("foodExhaustionLevel", NbtUpdater.foodExhaustionLevel);
    }

    public static void loadNbt(NbtCompound nbt) {
        if (nbt.contains("foodLevel", NbtElement.NUMBER_TYPE)) {
            NbtUpdater.foodLevel = nbt.getInt("foodLevel");
            NbtUpdater.foodTickTimer = nbt.getInt("foodTickTimer");
            NbtUpdater.foodSaturationLevel = nbt.getFloat("foodSaturationLevel");
            NbtUpdater.foodExhaustionLevel = nbt.getFloat("foodExhaustionLevel");
        }
    }
}

