package dev.emilahmaboy.felixagairu.saturative_overhaul.api;

import dev.emilahmaboy.felixagairu.saturative_overhaul.api.SOConfig;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class Helper {
    public static int foodLevel;
    public static int foodTickTimer;
    public static float foodSaturationLevel;
    public static float foodExhaustionLevel;
    public static float nutritionModifier = 5.0F;


    public static void saveNbt(NbtCompound nbt) {
        nbt.putInt("foodLevel", Helper.foodLevel);
        nbt.putInt("foodTickTimer", Helper.foodTickTimer);
        nbt.putFloat("foodSaturationLevel", Helper.foodSaturationLevel);
        nbt.putFloat("foodExhaustionLevel", Helper.foodExhaustionLevel);
    }

    public static void loadNbt(NbtCompound nbt) {
        if (nbt.contains("foodLevel", NbtElement.NUMBER_TYPE)) {
            Helper.foodLevel = nbt.getInt("foodLevel");
            Helper.foodTickTimer = nbt.getInt("foodTickTimer");
            Helper.foodSaturationLevel = nbt.getFloat("foodSaturationLevel");
            Helper.foodExhaustionLevel = nbt.getFloat("foodExhaustionLevel");
        }
    }
}

