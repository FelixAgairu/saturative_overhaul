package dev.emilahmaboy.felixagairu.saturative_overhaul.mixin;

import dev.emilahmaboy.felixagairu.saturative_overhaul.api.HungerManagerValues;
import dev.emilahmaboy.felixagairu.saturative_overhaul.integrations.farmersdelight.FarmersDelightModEffectsInstance;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.emilahmaboy.felixagairu.saturative_overhaul.api.HungerManagerValues.foodBarDelta;
import static dev.emilahmaboy.felixagairu.saturative_overhaul.api.HungerManagerValues.foodBarSize;


@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow private int ticks;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private static Identifier FOOD_EMPTY_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_TEXTURE;
    @Shadow @Final private static Identifier FOOD_EMPTY_HUNGER_TEXTURE;
    @Shadow @Final private static Identifier FOOD_FULL_HUNGER_TEXTURE;

    @Shadow @Final private Random random;

    @Shadow private long heartJumpEndTick;
    @Unique
    private static Identifier MOD_ICONS_TEXTURE;
    static {
        if (FabricLoader.getInstance().isModLoaded("farmersdelight")) {
            MOD_ICONS_TEXTURE = Identifier.of("farmersdelight", "textures/gui/fd_icons.png");
        }
    }

    @Inject(method = "renderFood", at = @At("HEAD"))
    private void renderFoodBar(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        renderFood(context, this.getCameraPlayer(), top, right);
    }

    @ModifyConstant(
            method = "renderFood",
            constant = @Constant(
                    intValue = 10,
                    ordinal = 0
            )
    )
    private int noFoodBarRender(int ignored) {
        return 0;
    }

    @Unique
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right) {
        HungerManager hungerManager = player.getHungerManager();

        float foodLevel = (float) hungerManager.getFoodLevel();
        float foodSegment = 0F;
        //float nearestFoodSegment;
        float fFoodBarSize = (float) foodBarSize;
        //float brilliance;
        int iconWidth = 0;

        Identifier iconsFull;
        Identifier iconsEmpty;
        int uFull;
        int uEmpty;
        int vFull;
        int vEmpty;
        boolean isUV;

        iconsFull = FOOD_FULL_TEXTURE;
        iconsEmpty = FOOD_EMPTY_TEXTURE;
        uFull = 0;
        uEmpty = 0;
        vFull = 0;
        vEmpty = 0;
        isUV = false;



        if (FarmersDelightModEffectsInstance.getInstance().hasNourishmentEffect(player)) {
            iconsFull = MOD_ICONS_TEXTURE;
            iconsEmpty = MOD_ICONS_TEXTURE;
            uFull = 18 + ((foodLevel >= 280.0F || (foodLevel > 120.0F && (hungerManager.getSaturationLevel() <= 1.0F || hungerManager.getSaturationLevel() > 3.0F))) ? 18 : 0);
            isUV = true;
        }

        int rightEdge = right - 9;

        for (int i = 0; i < foodBarSize; i++) {
            int _uFull = uFull;
            int _uEmpty = uEmpty;
            int _vFull = vFull;
            int _vEmpty = vEmpty;

            int y = top;
            if (HungerManagerValues.of((int) foodLevel, hungerManager.getSaturationLevel()).isFoodBarShouldBeAnimated(ticks)) {
                y += this.random.nextInt(3) - 1;
            }
            int x = rightEdge - foodBarDelta * i;
            // Hunger
            if (player.hasStatusEffect(StatusEffects.HUNGER)) {
                if (iconsFull == FOOD_FULL_TEXTURE) {
                    iconsFull = FOOD_FULL_HUNGER_TEXTURE;
                    iconsEmpty = FOOD_EMPTY_HUNGER_TEXTURE;
                    uFull = 0;
                    isUV = false;
                }
            }
            this.drawFoodIcon(context, iconsEmpty, x, y, _uEmpty, _vEmpty, 9, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);

            if (foodLevel < 100) {
                foodSegment = 100.0F / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil(foodLevel / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 0.45F, 0.83F, 0.69F, 0.75F, isUV);
                }
                if (Math.ceil(foodLevel / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else if (foodLevel < 300) {
                foodSegment = 200.0F / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil((foodLevel - 100) / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                }
                if (Math.ceil((foodLevel - 100) / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else if (foodLevel < 350) {
                foodSegment = 50.0F / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil((foodLevel - 300) / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 0.7F, 0.3F, 1.0F, isUV);
                }
                if (Math.ceil((foodLevel - 300) / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else {
                float multiplier = 1.0F / (float) Math.max(1.0F, Math.pow(foodLevel - 300.0F, 0.1F));
                this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 0.7F * multiplier, 0.3F * multiplier, 1.0F, isUV);
            }
        }
    }

    @Unique
    private void drawFoodIcon(DrawContext context, Identifier icon, int x, int y, int u, int v, int width, int height, float r, float g, float b, float a, boolean isUV) {
        context.setShaderColor(r, g, b, a);
        
		if (isUV) {
            context.drawTexture(icon, x, y, u, v, width, height);
        } else {
            context.enableScissor(x, y, x + width, y + height);
            context.drawGuiTexture(icon, x, y, Math.max(width, height), Math.max(width, height));
            context.disableScissor();
        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}