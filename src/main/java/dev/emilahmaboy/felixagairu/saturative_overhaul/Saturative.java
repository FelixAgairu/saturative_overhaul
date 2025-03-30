package dev.emilahmaboy.felixagairu.saturative_overhaul;

import dev.emilahmaboy.felixagairu.saturative_overhaul.api.SOConfig;
import net.fabricmc.api.ModInitializer;


public class Saturative implements ModInitializer {
    public static final SOConfig CONFIG = SOConfig.createAndLoad();
    @Override
    public void onInitialize() {}
}
