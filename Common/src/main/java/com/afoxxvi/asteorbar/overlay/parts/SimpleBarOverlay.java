package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public abstract class SimpleBarOverlay extends BaseOverlay {
    protected long lastChangeMillis = 0;
    private Parameters lastParameters = new Parameters();

    public static class Parameters {
        public int fillColor = 0;
        public int fillColor2 = 0;
        public int boundColor = 0;
        public int boundColor2 = 0;
        public int emptyColor = 0;
        public double value = 0;
        public double capacity = 1;
        public int boundFillColor = 0;
        public double boundValue = 0;
        public double boundCapacity = 1;
        public String centerText = null;
        public String leftText = null;
        public String rightText = null;
        public int centerColor = 0;
        public int leftColor = 0;
        public int rightColor = 0;

        public Parameters() {
        }

        public boolean valueEquals(Parameters other) {
            return value == other.value && capacity == other.capacity && boundValue == other.boundValue && boundCapacity == other.boundCapacity && Objects.equals(centerText, other.centerText) && Objects.equals(leftText, other.leftText) && Objects.equals(rightText, other.rightText);
        }
    }

    protected void drawDecorations(GuiGraphics guiGraphics, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
    }

    private void draw(GuiGraphics guiGraphics, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
        if (parameters == null) return;
        if (parameters.boundColor2 == 0) {
            drawBound(guiGraphics, left, top, right, bottom, parameters.boundColor);
        } else {
            drawBound(guiGraphics, left, top, right, bottom, parameters.boundColor, parameters.boundColor2);
        }
        drawEmptyFill(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, parameters.emptyColor);
        final int innerWidth = right - left - 2;
        final int fillWidth = (int) (innerWidth * parameters.value / parameters.capacity);
        if (parameters.fillColor2 != 0) {
            drawFillFlip(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, fillWidth, parameters.fillColor, parameters.fillColor2, flip);
        } else {
            drawFillFlip(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, fillWidth, parameters.fillColor, flip);
        }
        if (parameters.boundFillColor != 0) {
            final int boundFillWidth = (int) (innerWidth * parameters.boundValue / parameters.boundCapacity);
            drawBoundFlip(guiGraphics, left, top, right, bottom, boundFillWidth, parameters.boundFillColor, flip);
        }
        if (parameters.centerText != null) {
            Overlays.addStringRender((left + right) / 2, top - 2, parameters.centerColor, parameters.centerText, Overlays.ALIGN_CENTER, true);
        }
        if (parameters.leftText != null) {
            if (flip) {
                Overlays.addStringRender(right - 2, top - 2, parameters.leftColor, parameters.leftText, Overlays.ALIGN_RIGHT, true);
            } else {
                Overlays.addStringRender(left + 2, top - 2, parameters.leftColor, parameters.leftText, Overlays.ALIGN_LEFT, true);
            }
        }
        if (parameters.rightText != null) {
            if (flip) {
                Overlays.addStringRender(left + 2, top - 2, parameters.rightColor, parameters.rightText, Overlays.ALIGN_LEFT, true);
            } else {
                Overlays.addStringRender(right - 2, top - 2, parameters.rightColor, parameters.rightText, Overlays.ALIGN_RIGHT, true);
            }
        }
        drawDecorations(guiGraphics, left, top, right, bottom, parameters, flip);
    }

    protected abstract Parameters getParameters(Player player);

    protected abstract boolean shouldRender(Player player);

    protected boolean isLeftSide() {
        return false;
    }

    protected boolean alwaysLow() {
        return false;
    }

    protected boolean canHide() {
        return true;
    }

    @Override
    public void renderOverlay(RenderGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        var player = gui.mc().player;
        if (player == null) return;
        if (!shouldRender(player)) return;
        var parameters = getParameters(player);
        if (parameters == null) return;
        boolean recoverShaderColor = false;
        if (canHide()) {
            if (!parameters.valueEquals(lastParameters)) {
                lastChangeMillis = System.currentTimeMillis();
            }
            var wait = AsteorBar.config.hideUnchangingBarAfterSeconds() * 1000;
            if (wait > 0) {
                if (System.currentTimeMillis() - lastChangeMillis > wait + 1000) {
                    return;
                } else if (System.currentTimeMillis() - lastChangeMillis > wait) {
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F - (System.currentTimeMillis() - lastChangeMillis - wait) / 1000.0F);
                    recoverShaderColor = true;
                }
            }
        }
        int left, top, right;
        boolean flip;
        int style = Overlays.style;
        if (alwaysLow()) {
            style = Overlays.STYLE_ABOVE_HOT_BAR_SHORT;
        }
        switch (style) {
            case Overlays.STYLE_ABOVE_HOT_BAR_LONG, Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                if (isLeftSide()) {
                    left = screenWidth / 2 - 91;
                    top = screenHeight - gui.leftHeight() + 4;
                    right = left + BOUND_FULL_WIDTH_SHORT;
                    flip = false;
                    gui.leftHeight(6);
                } else {
                    left = screenWidth / 2 + 10;
                    top = screenHeight - gui.rightHeight() + 4;
                    right = left + BOUND_FULL_WIDTH_SHORT;
                    flip = true;
                    gui.rightHeight(6);
                    draw(guiGraphics, left, top, left + BOUND_FULL_WIDTH_SHORT, top + 5, parameters, true);
                }
            }
            case Overlays.STYLE_TOP_LEFT -> {
                top = Overlays.vertical;
                left = Overlays.horizontal;
                right = left + Overlays.length;
                flip = false;
                Overlays.vertical += 6;
            }
            case Overlays.STYLE_TOP_RIGHT -> {
                top = Overlays.vertical;
                left = screenWidth - Overlays.length - Overlays.horizontal;
                right = left + Overlays.length;
                flip = true;
                Overlays.vertical += 6;
            }
            case Overlays.STYLE_BOTTOM_LEFT -> {
                top = screenHeight - Overlays.vertical;
                left = Overlays.horizontal;
                right = left + Overlays.length;
                flip = false;
                Overlays.vertical += 6;
            }
            case Overlays.STYLE_BOTTOM_RIGHT -> {
                top = screenHeight - Overlays.vertical;
                left = screenWidth - Overlays.length - Overlays.horizontal;
                right = left + Overlays.length;
                flip = true;
                Overlays.vertical += 6;
            }
            default -> {
                return;
            }
        }
        draw(guiGraphics, left, top, right, top + 5, parameters, flip);
        lastParameters = parameters;
        if (recoverShaderColor) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
