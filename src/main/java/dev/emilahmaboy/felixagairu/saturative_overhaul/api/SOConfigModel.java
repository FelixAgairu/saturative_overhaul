package dev.emilahmaboy.felixagairu.saturative_overhaul.api;


import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Sync;
import io.wispforest.owo.config.Option;

@Config(name = "saturative-overhaul-config", wrapperName = "SOConfig")
public class SOConfigModel {
    public int xMaxFoodLevel = 400;
    public float xDefaultSaturationLevel = 2.0f;

    public int xDefaultFoodLevel = 220;
}