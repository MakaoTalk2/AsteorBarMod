package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseOverlay {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AsteorBar.MOD_ID, "textures/gui/overlay.png");
    public static final ResourceLocation LIGHTMAP_TEXTURE = new ResourceLocation(AsteorBar.MOD_ID, "textures/ui/lightmap.png");
    public static final int FILL_FULL_WIDTH_LONG = 180;
    public static final int BOUND_FULL_WIDTH_LONG = 182;
    public static final int BOUND_FULL_WIDTH_SHORT = 81;
    public static final int Y_REGENERATION_FILL = 0;
    public static final int Y_FOOD_EXHAUSTION_FILL = 9;
    public static final int Y_EXPERIENCE_DECORATION = 18;
    public static final int Y_RIGHT_DECORATION = 27;
    public static final int Y_LEFT_DECORATION = 36;

    public List<BaseOverlay> overrideOverlay = new ArrayList<>();
    protected int tick = 0;

    public boolean shouldOverride() {
        return false;
    }

    protected void drawTextureFill(PoseStack poseStack, int left, int top, int width, int height, int textureX, int textureY) {
        GuiHelper.drawTexturedRect(poseStack, left, top, textureX, textureY, width, height);
    }

    protected void drawTextureFillColor(PoseStack poseStack, int left, int top, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight, int color) {
        GuiHelper.drawTexturedRectColor(poseStack, left, top, left + width, top + height, textureX, textureY, textureX + textureWidth, textureY + textureHeight, 256, 256, color);
    }

    protected void drawTextureFillFlip(PoseStack poseStack, int left, int top, int right, int width, int height, int textureX, int textureY, int textureFullWidth, boolean flip) {
        if (flip) {
            GuiHelper.drawTexturedRect(poseStack, right - width, top, textureX + textureFullWidth - width, textureY, width, height);
        } else {
            GuiHelper.drawTexturedRect(poseStack, left, top, textureX, textureY, width, height);
        }
    }

    protected void drawEmptyFill(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        GuiHelper.drawSolidGradient(poseStack, left, top, right, bottom, color);
    }

    protected void drawFillFlipConcat(PoseStack poseStack, int left, int top, int right, int bottom, int has, int width, int color, boolean flip) {
        if (has == 0) {
            drawFillFlip(poseStack, left, top, right, bottom, width, color, flip);
            return;
        }
        width = Math.max(0, Math.min(right - left - has, width));
        if (flip) {
            GuiHelper.drawSolidGradientUpDown(poseStack, right - has - width, top, right - has, bottom, color);
        } else {
            GuiHelper.drawSolidGradientUpDown(poseStack, left + has, top, left + has + width, bottom, color);
        }
    }

    protected void drawFillFlip(PoseStack poseStack, int left, int top, int right, int bottom, int width, int color, boolean flip) {
        width = Math.max(0, Math.min(right - left, width));
        if (flip) {
            GuiHelper.drawSolidGradientUpDown(poseStack, right - width, top, right, bottom, color);
        } else {
            GuiHelper.drawSolidGradientUpDown(poseStack, left, top, left + width, bottom, color);
        }
    }

    protected void drawFillFlip(PoseStack poseStack, int left, int top, int right, int bottom, int width, int color, int color2, boolean flip) {
        width = Math.max(0, Math.min(right - left, width));
        drawFillFlip(poseStack, left, top, right, bottom, width, color, flip);
        if (flip) {
            GuiHelper.drawSolidColor(poseStack, right - width, bottom - 1, right, bottom, color2);
        } else {
            GuiHelper.drawSolidColor(poseStack, left, bottom - 1, left + width, bottom, color2);
        }
    }

    protected void drawBoundFlipConcat(PoseStack poseStack, int left, int top, int right, int bottom, int has, int width, int color, boolean flip) {
        if (has == 0) {
            drawBoundFlip(poseStack, left, top, right, bottom, width, color, flip);
            return;
        }
        width = Math.max(0, Math.min(right - left - has, width));
        if (width == 0) return;
        if (flip) {
            if (has + width >= right - left) {
                GuiHelper.drawSolidColor(poseStack, left, top + 1, left + 1, bottom - 1, color);
                width--;
            }
            //note that 1 pixel of 'has' is drawn as right bound
            GuiHelper.drawSolidColor(poseStack, right - has - width, top, right - has, top + 1, color);
            GuiHelper.drawSolidColor(poseStack, right - has - width, bottom - 1, right - has, bottom, color);
        } else {
            if (has + width >= right - left) {
                GuiHelper.drawSolidColor(poseStack, right - 1, top + 1, right, bottom - 1, color);
                width--;
            }
            GuiHelper.drawSolidColor(poseStack, left + has, top, left + has + width, top + 1, color);
            GuiHelper.drawSolidColor(poseStack, left + has, bottom - 1, left + has + width, bottom, color);
        }
    }

    protected void drawBoundFlip(PoseStack poseStack, int left, int top, int right, int bottom, int width, int color, boolean flip) {
        width = Math.max(0, Math.min(right - left, width));
        if (width == 0) return;
        if (width == right - left) {
            drawBound(poseStack, left, top, right, bottom, color);
            return;
        }
        if (flip) {
            GuiHelper.drawSolidColor(poseStack, right - 1, top + 1, right, bottom - 1, color);
            if (width > 1) {
                GuiHelper.drawSolidColor(poseStack, right - 1 - width + 1, top, right - 1, top + 1, color);
                GuiHelper.drawSolidColor(poseStack, right - 1 - width + 1, bottom - 1, right - 1, bottom, color);
            }
        } else {
            GuiHelper.drawSolidColor(poseStack, left, top + 1, left + 1, bottom - 1, color);
            if (width > 1) {
                GuiHelper.drawSolidColor(poseStack, left + 1, top, left + 1 + width - 1, top + 1, color);
                GuiHelper.drawSolidColor(poseStack, left + 1, bottom - 1, left + 1 + width - 1, bottom, color);
            }
        }
    }

    protected void drawBound(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        GuiHelper.drawSolidColor(poseStack, left, top + 1, left + 1, bottom - 1, color);
        GuiHelper.drawSolidColor(poseStack, right - 1, top + 1, right, bottom - 1, color);
        GuiHelper.drawSolidColor(poseStack, left + 1, top, right - 1, top + 1, color);
        GuiHelper.drawSolidColor(poseStack, left + 1, bottom - 1, right - 1, bottom, color);
    }

    protected void drawBound(PoseStack poseStack, int left, int top, int right, int bottom, int color, int color2) {
        GuiHelper.drawSolidColor(poseStack, left, top + 1, left + 1, bottom - 2, color);
        GuiHelper.drawSolidColor(poseStack, right - 1, top + 1, right, bottom - 2, color);
        GuiHelper.drawSolidColor(poseStack, left + 1, top, right - 1, top + 1, color);
        GuiHelper.drawSolidColor(poseStack, left, bottom - 2, left + 1, bottom - 1, color2);
        GuiHelper.drawSolidColor(poseStack, right - 1, bottom - 2, right, bottom - 1, color2);
        GuiHelper.drawSolidColor(poseStack, left + 1, bottom - 1, right - 1, bottom, color2);
    }

    public void render(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (AsteorBar.config.enableOverlay()) {
            tick = gui.gui().getGuiTicks();
            RenderSystem.setShaderTexture(0, LIGHTMAP_TEXTURE);
            for (BaseOverlay baseOverlay : overrideOverlay) {
                if (baseOverlay.shouldOverride()) {
                    baseOverlay.render(gui, poseStack, partialTick, screenWidth, screenHeight);
                    return;
                }
            }
            renderOverlay(gui, poseStack, partialTick, screenWidth, screenHeight);
        }
    }

    public abstract void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight);
}
