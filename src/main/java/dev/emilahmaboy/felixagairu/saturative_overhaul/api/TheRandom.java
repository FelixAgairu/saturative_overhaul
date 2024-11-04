package dev.emilahmaboy.felixagairu.saturative_overhaul.api;

public class TheRandom {
    public static float fTheRandom(float in, float lowMulti, float highMulti) {
        return in * lowMulti + (in * highMulti - in * lowMulti) * (float) Math.random();
    }
}
