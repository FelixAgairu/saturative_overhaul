package dev.emilahmaboy.felixagairu.saturative_overhaul.api;

public class HungerManagerValues {
    public static final int maxFoodLevel = 400;
    public static final int defaultFoodLevel = 160;
    public static final float defaultSaturationLevel = 2.0f;
    public static final float defaultExhaustionLevel = 0.0f;
    public static final int nutritionModifier = 5;
    public static final int foodBarSize = 10;
    public static final int foodBarDelta = 8;

    private float exhaustionLevel;
    private float saturationLevel;
    private int foodLevel;
    private int foodTickTimer;

    private HungerManagerValues(int foodLevel, float saturationLevel, float exhaustionLevel, int foodTickTimer) {
        this.exhaustionLevel = exhaustionLevel;
        this.saturationLevel = saturationLevel;
        this.foodLevel = foodLevel;
        this.foodTickTimer = foodTickTimer;
    }

    public static HungerManagerValues of() {
        return new HungerManagerValues(defaultFoodLevel, defaultSaturationLevel, defaultExhaustionLevel, 0);
    }

    public static HungerManagerValues of(int foodLevel) {
        return new HungerManagerValues(foodLevel, defaultSaturationLevel, defaultExhaustionLevel, 0);
    }

    public static HungerManagerValues of(int foodLevel, float saturationLevel) {
        return new HungerManagerValues(foodLevel, saturationLevel, defaultExhaustionLevel, 0);
    }

    public static HungerManagerValues of(int foodLevel, float saturationLevel, float exhaustionLevel) {
        return new HungerManagerValues(foodLevel, saturationLevel, exhaustionLevel, 0);
    }

    public static HungerManagerValues of(int foodLevel, float saturationLevel, float exhaustionLevel, int foodTickTimer) {
        return new HungerManagerValues(foodLevel, saturationLevel, exhaustionLevel, foodTickTimer);
    }

    public static HungerManagerValues of(int foodLevel, float saturationLevel, int foodTickTimer, float exhaustionLevel) {
        return new HungerManagerValues(foodLevel, saturationLevel, exhaustionLevel, foodTickTimer);
    }

    public float getExhaustionLevel() {
        return exhaustionLevel;
    }

    public float getSaturationLevel() {
        return saturationLevel;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public int getFoodTickTimer() {
        return foodTickTimer;
    }

    public HungerManagerValues setExhaustionLevel(float exhaustionLevel) {
        this.exhaustionLevel = exhaustionLevel;
        return this;
    }

    public HungerManagerValues setSaturationLevel(float saturationLevel) {
        this.saturationLevel = saturationLevel;
        return this;
    }

    public HungerManagerValues setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
        return this;
    }

    public HungerManagerValues setFoodTickTimer(int foodTickTimer) {
        this.foodTickTimer = foodTickTimer;
        return this;
    }

    public HungerManagerValues addExhaustionLevel(float exhaustionLevel) {
        this.exhaustionLevel = Math.max(0.0f, this.exhaustionLevel + exhaustionLevel);
        return this;
    }

    public HungerManagerValues trimExhaustionLevel() {
        this.addExhaustionLevel(0.0f);
        return this;
    }

    public HungerManagerValues addSaturationLevel(float saturationLevel) {
        this.saturationLevel = Math.min(Math.max(0.0f, this.saturationLevel + saturationLevel), (float) this.foodLevel / 18.0F);
        return this;
    }

    public HungerManagerValues trimSaturationLevel() {
        this.addSaturationLevel(0.0f);
        return this;
    }

    public HungerManagerValues addFoodLevel(int foodLevel) {
        this.foodLevel = Math.min(Math.max(0, this.foodLevel + foodLevel), maxFoodLevel);
        return this;
    }

    public HungerManagerValues trimFoodLevel() {
        this.addFoodLevel(0);
        return this;
    }

    public HungerManagerValues addFoodTickTimer(int foodTickTimer) {
        this.foodTickTimer = Math.max(0, this.foodTickTimer + foodTickTimer);
        return this;
    }

    public HungerManagerValues tickFoodTickTimer() {
        this.foodTickTimer += 1;
        return this;
    }

    public boolean foodTickTimerReached(int destination) {
        return this.foodTickTimer >= destination;
    }

    public HungerManagerValues resetFoodTickTimer() {
        this.foodTickTimer = 0;
        return this;
    }

    public boolean isCritical() {
        return this.foodLevel <= 40 || this.foodLevel >= 300;
    }

    public boolean isFoodBarShouldBeAnimated(int ticks) {
        if (this.isCritical()) {
            return true;
        } else return this.saturationLevel <= 0.0F && ticks % foodLevel == 0;
    }

    @Override
    public String toString() {
        return "{foodLevel: " + this.foodLevel + ", saturationLevel: " + this.saturationLevel + ", exhaustionLevel: " + this.exhaustionLevel + ", foodTickTimer: " + this.foodTickTimer + "}";
    }
}
