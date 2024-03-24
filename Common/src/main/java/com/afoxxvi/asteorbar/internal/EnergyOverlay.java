package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.BaseOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

public class EnergyOverlay extends BaseOverlay {
    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, boolean highlight, double manaLevel, boolean flip) {
        draw(poseStack, left, top, right, bottom, highlight, manaLevel, flip, false);
    }

    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, boolean highlight, double manaLevel, boolean flip, boolean vertical) {
        int color = 0xffe67f09;
        int dark = Utils.mixColor(0xFF000000, color, 0.5);
        int light = Utils.mixColor(0xFFFFFFFF, color, 0.5);
        int empty = Utils.mixColor(0xFF000000, color, 0.75);
        if (highlight) {
            drawBound(poseStack, left, top, right, bottom, light);
        } else {
            drawBound(poseStack, left, top, right, bottom, dark);
        }
        if (vertical) {
            int height = (int) ((bottom - top - 2) * manaLevel);
            drawEmptyFillVertical(poseStack, left + 1, top + 1, right - 1, bottom - 1, empty);
            drawFillVertical(poseStack, left + 1, top + 1, right - 1, bottom - 1, height, color);
            int lineHeight = (int) (Minecraft.getInstance().font.lineHeight * AsteorBar.config.overlayTextScale());
            Overlays.addStringRender((left + right) / 2, (bottom + top - lineHeight) / 2, 0xFFFFFF, String.format("%.0f", InternalInfo.energy * 0.1), Overlays.ALIGN_CENTER, true);
            Overlays.addStringRender((left + right) / 2, bottom - lineHeight - 2, 0xFFFFFF, String.format("%.0f", InternalInfo.energyMax * 0.1), Overlays.ALIGN_CENTER, false, true, 0);
        } else {
            int width = (int) ((right - left - 2) * manaLevel);
            drawEmptyFill(poseStack, left + 1, top + 1, right - 1, bottom - 1, empty);
            drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, width, color, flip);
            Overlays.addStringRender((left + right) / 2, top - 2, 0xFFFFFF, String.format("%.0f/%.0f", InternalInfo.energy * 0.1, InternalInfo.energyMax * 0.1), Overlays.ALIGN_CENTER, true);
        }
    }

    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!InternalInfo.activated) {
            return;
        }
        double energyLevel = (double) (InternalInfo.energy) / Math.max(1.0, InternalInfo.energyMax);
        boolean highlight = AsteorBar.tick - InternalInfo.energyGlint < 4;
        switch (Overlays.style) {
            case Overlays.INTERNAL_STYLE_ABOVE_HOT_BAR_NORMAL -> {
                int left = screenWidth / 2 - 91;
                int top = screenHeight - gui.leftHeight() + 4;
                draw(poseStack, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, highlight, energyLevel, false);
                gui.leftHeight(12);
            }
            default -> {
            }
        }
    }
}
