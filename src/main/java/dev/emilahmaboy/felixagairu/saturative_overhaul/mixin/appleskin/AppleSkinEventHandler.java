package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin.appleskin;

import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.api.event.FoodValuesEvent;

public class AppleSkinEventHandler implements AppleSkinApi {
    @Override
    public void registerEvents() {
        FoodValuesEvent.EVENT.register(foodValuesEvent -> {
            // display hunger restored as 1 for all food
        });
    }
}