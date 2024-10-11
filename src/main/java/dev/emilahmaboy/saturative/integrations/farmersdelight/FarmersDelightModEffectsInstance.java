package dev.emilahmaboy.saturative.integrations.farmersdelight;


import net.fabricmc.loader.api.FabricLoader;

import java.util.ServiceLoader;

public class FarmersDelightModEffectsInstance {;
    private static FarmersDelightModEffectsService instance = null;

    public static FarmersDelightModEffectsService getInstance() {
        if (instance == null) {
            if (!FabricLoader.getInstance().isModLoaded("farmersdelight")) {
                instance = new FarmersDelightModEffectsImplFallback();
            } else {
                ServiceLoader<FarmersDelightModEffectsService> loader = ServiceLoader.load(FarmersDelightModEffectsService.class);
                instance = loader.findFirst().orElseThrow();
            }
        }
        return instance;
    }
}
