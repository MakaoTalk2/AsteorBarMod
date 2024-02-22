package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.BaseOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

public class ManaOverlay extends BaseOverlay {

    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, boolean highlight, int manaType, double manaLevel, boolean flip) {
        draw(poseStack, left, top, right, bottom, highlight, manaType, manaLevel, flip, false);
    }

    private void draw(PoseStack poseStack, int left, int top, int right, int bottom, boolean highlight, int manaType, double manaLevel, boolean flip, boolean vertical) {
        int color = 0xff1976d2;
        if (manaType == 1) {
            color = 0xff5e35b1;
        }
        int dark = Utils.mixColor(0xFF000000, color, 0.5);
        int light = Utils.mixColor(0xFFFFFFFF, color, 0.5);
        if (highlight) {
            drawBound(poseStack, left, top, right, bottom, light);
        } else {
            drawBound(poseStack, left, top, right, bottom, dark);
        }
        if (vertical) {
            int height = (int) ((bottom - top - 2) * manaLevel);
            drawEmptyFillVertical(poseStack, left + 1, top + 1, right - 1, bottom - 1, 0xff808080);
            drawFillVertical(poseStack, left + 1, top + 1, right - 1, bottom - 1, height, color);
            int lineHeight = (int) (Minecraft.getInstance().font.lineHeight * AsteorBar.config.overlayTextScale());
            Overlays.addStringRender((left + right) / 2, (bottom + top - lineHeight) / 2, 0xFFFFFF, String.format("%.0f", InternalInfo.mana * 0.1), Overlays.ALIGN_CENTER, true);
            Overlays.addStringRender((left + right) / 2, bottom - lineHeight - 2, 0xFFFFFF, String.format("%.0f", InternalInfo.manaMax * 0.1), Overlays.ALIGN_CENTER, false, true, 0);
        } else {
            int width = (int) ((right - left - 2) * manaLevel);
            drawEmptyFill(poseStack, left + 1, top + 1, right - 1, bottom - 1, 0xff808080);
            drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, width, color, flip);
            Overlays.addStringRender((left + right) / 2, top - 2, 0xFFFFFF, String.format("%.0f/%.0f", InternalInfo.mana * 0.1, InternalInfo.manaMax * 0.1), Overlays.ALIGN_CENTER, true);
        }
    }

    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!InternalInfo.activated) {
            return;
        }
        double manaLevel = (double) (InternalInfo.mana) / Math.max(1.0, InternalInfo.manaMax);
        boolean highlight = AsteorBar.tick - InternalInfo.manaGlint < 4;
        int manaType = InternalInfo.manaType;
        switch (Overlays.style) {
            case Overlays.INTERNAL_STYLE_ABOVE_HOT_BAR_LONG -> {
                int left = screenWidth / 2 + 31;
                int top = screenHeight - gui.rightHeight() + 4;
                gui.rightHeight(12);
                draw(poseStack, left, top, left + 60, top + 5, highlight, manaType, manaLevel, false);
            }
            case Overlays.INTERNAL_STYLE_ABOVE_HOT_BAR_SHORT -> {
                int left = screenWidth / 2 + 10;
                int top = screenHeight - gui.rightHeight() + 4;
                gui.rightHeight(6);
                draw(poseStack, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, highlight, manaType, manaLevel, true);
            }
            case Overlays.INTERNAL_STYLE_VERTICAL_ALONGSIDE_BOTTOM -> {
                int left = screenWidth / 2 + Overlays.rightShift;
                int top = screenHeight - Overlays.height;
                Overlays.rightShift += AsteorBar.config.internalBarWidth() + 1;
                draw(poseStack, left, top, left + AsteorBar.config.internalBarWidth(), screenHeight, highlight, manaType, manaLevel, false, true);
            }
            default -> {
            }
        }
    }
}
