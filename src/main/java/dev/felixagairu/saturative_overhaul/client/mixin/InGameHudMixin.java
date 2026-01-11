/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
// This file includes modifications by Felix.
// Licensed under the MPL 2.0. Original portions © saturative upstream under MIT by EmilAhmaBoy.

package dev.felixagairu.saturative_overhaul.client.mixin;

/*? >=1.21.6 {*/
/*import net.minecraft.client.gl.RenderPipelines;
*//*?}*/
import dev.felixagairu.saturative_overhaul.util.ConfigHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
/*? >=1.21.2 {*/
/*import net.minecraft.client.render.RenderLayer;
*//*?}*/
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private static Identifier FOOD_EMPTY_TEXTURE;
    @Shadow
    @Final
    private static Identifier FOOD_FULL_TEXTURE;
    @Shadow
    @Final
    private static Identifier FOOD_EMPTY_HUNGER_TEXTURE;
    @Shadow
    @Final
    private static Identifier FOOD_FULL_HUNGER_TEXTURE;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

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

    /*? >=1.21.2 {*/
    /*@Unique
    private static int premultiply(float r, float g, float b, float a) {
        int ia = (int)(a * 255);
        int ir = (int)(r * a * 255);
        int ig = (int)(g * a * 255);
        int ib = (int)(b * a * 255);

        return (ia << 24) | (ir << 16) | (ig << 8) | ib;
    }*//*?}*/

    @Unique
    private void renderFood(DrawContext context, PlayerEntity player, int top, int right) {
        HungerManager hungerManager = player.getHungerManager();

        float foodLevel = (float) hungerManager.getFoodLevel();
        float foodSegment = 0F;
        //float nearestFoodSegment;
        int foodBarSize = 10;
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


        int rightEdge = right - 9;

        for (int i = 0; i < foodBarSize; i++) {
            int _uFull = uFull;
            int _uEmpty = uEmpty;
            int _vFull = vFull;
            int _vEmpty = vEmpty;

            int y = top;

            int foodBarDelta = 8;
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

            if (foodLevel < ConfigHelper.guiStarving) {
                // Food Bar Starving
                // "Hunger II"
                foodSegment = (float) ConfigHelper.guiStarving / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil(foodLevel / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 0.45F, 0.83F, 0.69F, 0.75F, isUV);
                }
                if (Math.ceil(foodLevel / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else if (foodLevel < ConfigHelper.guiNormal) {
                // Normal
                foodSegment = (float) (ConfigHelper.guiNormal - ConfigHelper.guiStarving) / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil((foodLevel - (float) ConfigHelper.guiStarving) / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                }
                if (Math.ceil((foodLevel - (float) ConfigHelper.guiStarving) / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else if (foodLevel < ConfigHelper.guiOvereating) {
                // Food Bar Overeating
                // "Overeat I"
                foodSegment = (float) (ConfigHelper.guiOvereating - ConfigHelper.guiNormal - ConfigHelper.guiStarving) / fFoodBarSize;
                iconWidth = Math.round((1 - foodLevel % foodSegment / foodSegment) * 9.0F);
                if (Math.ceil((foodLevel - (float) (ConfigHelper.guiOvereating - ConfigHelper.guiNormal - ConfigHelper.guiStarving)) / foodSegment) > i) {
                    this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 0.7F, 0.3F, 1.0F, isUV);
                }
                if (Math.ceil((foodLevel - (float) (ConfigHelper.guiOvereating - ConfigHelper.guiNormal - ConfigHelper.guiStarving)) / foodSegment) == i + 1) {
                    if (!((foodLevel % foodSegment) / foodSegment == 0)) {
                        this.drawFoodIcon(context, iconsEmpty, x, y, _uFull, _vFull, iconWidth, 9, 1.0F, 1.0F, 1.0F, 1.0F, isUV);
                    }
                }
            } else {
                // Food Bar Overeating+
                // "Overeat II"
                float multiplier = 1.0F / (float) Math.max(1.0F, Math.pow(foodLevel - 300.0F, 0.1F));
                this.drawFoodIcon(context, iconsFull, x, y, _uFull, _vFull, 9, 9, 1.0F, 0.7F * multiplier, 0.3F * multiplier, 1.0F, isUV);
            }
        }
    }

    @Unique
    private void drawFoodIcon(
            DrawContext context,
            Identifier icon,
            int x, int y,
            int u, int v,
            int width, int height,
            float r, float g, float b, float a,
            boolean isUV
    ) {
        /*? >=1.21.2 {*/
        /*// Alpha‑aware, premultiplied ARGB
        int color = premultiply(r, g, b, a);

        if (isUV) {
            context.drawGuiTexture(
                    /^? >=1.21.6 {^/
                    /^RenderPipelines.GUI_TEXTURED,
                    icon,
                    Math.max(width, height),
                    Math.max(width, height),
                    u, v,
                    x, y,
                    Math.max(width, height),
                    Math.max(width, height),
                    color
                    ^//^?} else >= 1.21.1 {^/
                    RenderLayer::getGuiTextured,
                    icon,
                    Math.max(width, height),
                    Math.max(width, height),
                    u, v,
                    x, y,
                    Math.max(width, height),
                    Math.max(width, height)
                    /^?}^/
            );
        } else {
            context.enableScissor(x, y, x + width, y + height);

            context.drawGuiTexture(
                    /^? >=1.21.6 {^/
                    /^RenderPipelines.GUI_TEXTURED,
                    icon,
                    x, y,
                    Math.max(width, height),
                    Math.max(width, height),
                    color
                    ^//^?} else >= 1.21.1 {^/
                    RenderLayer::getGuiTextured,
                    icon,
                    x, y,
                    Math.max(width, height),
                    Math.max(width, height),
                    color
                    /^?}^/
            );
        }
        context.disableScissor();
        *//*?} else >=1.21.1 {*/
        context.setShaderColor(r, g, b, a);

        if (isUV) {
            context.drawTexture(icon, x, y, u, v, width, height);
        } else {
            context.enableScissor(x, y, x + width, y + height);
            context.drawGuiTexture(icon, x, y, Math.max(width, height), Math.max(width, height));
            context.disableScissor();
        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        /*?}*/
    }
}
