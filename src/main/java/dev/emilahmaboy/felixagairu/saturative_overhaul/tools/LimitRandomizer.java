package dev.emilahmaboy.felixagairu.saturative_overhaul.tools;

public class LimitRandomizer {
    public static float fTheRandom(float in, float lowMulti, float highMulti) {
        return in * lowMulti + (in * highMulti - in * lowMulti) * (float) Math.random();
    }
}
