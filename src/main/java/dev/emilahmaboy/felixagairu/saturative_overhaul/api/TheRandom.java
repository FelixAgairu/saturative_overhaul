package dev.emilahmaboy.felixagairu.saturative_overhaul.api;

public class TheRandom {
    public static double dTheRandom(double in, double lowMulti, double highMulti) {
        return in * lowMulti + (in * highMulti - in * lowMulti) * Math.random();
    }
}
