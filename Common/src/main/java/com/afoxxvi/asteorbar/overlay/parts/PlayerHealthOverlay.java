package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffects;


public class PlayerHealthOverlay extends BaseOverlay {
    public static final int ABSORPTION_MODE_TOGETHER = 0;
    public static final int ABSORPTION_MODE_STACK = 1;
    public static final int ABSORPTION_MODE_BOUND = 2;
    public static final int ABSORPTION_MODES = 3;
    public static final int ABSORPTION_TEXT_MODE_TOGETHER = 0;
    public static final int ABSORPTION_TEXT_MODE_SEPARATE = 1;
    public static final int ABSORPTION_TEXT_MODES = 2;
    private long healthBlinkTime = 0;
    private long lastHealthTime;
    private float lastHealth;
    private final int[] shift = new int[]{0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1};

    private int[] getStackColor(int low, int num) {
        final var colors = AsteorBar.config.stackHealthBarColors().split(",");
        final var color1 = low == 0 ? "#00000000" : colors[(low - 1) % colors.length];
        final var color2 = colors[low % colors.length];
        if (num == 2) return new int[]{Utils.parseHexColor(color1), Utils.parseHexColor(color2)};
        return new int[]{Utils.parseHexColor(color1), Utils.parseHexColor(color2), Utils.parseHexColor(colors[(low + 1) % colors.length])};
    }

    private void draw(GuiGraphics guiGraphics, int left, int top, int right, int bottom, boolean highlight, int healthColor, float health, float absorb, float maxHealth, float flashAlpha, int regenerationOffset, double healthIncrement, long tick, boolean flip) {
        int verticalShift = 0;
        if (health < maxHealth * AsteorBar.config.lowHealthRate()) {
            verticalShift = shift[(int) (tick % shift.length)];
            top += verticalShift;
            bottom += verticalShift;
        }
        //draw bound
        drawBound(poseStack, left, top, right, bottom, AsteorBar.config.healthBoundColor());
        drawEmptyFill(poseStack, left + 1, top + 1, right - 1, bottom - 1, AsteorBar.config.healthEmptyColor());
        final var outerLength = right - left;
        final var innerLength = outerLength - 2;
        int i = AsteorBar.config.displayAbsorptionMethod();
        if (AsteorBar.config.enableStackHealthBar()) {
            i = ABSORPTION_MODE_BOUND;
        }
        float alpha = (float) Math.cos(tick % 40 / 40.0 * Math.PI * 2) * 0.5F + 0.5F;
        if (i == ABSORPTION_MODE_TOGETHER) {
            //draw health
            int healthLength = (int) (innerLength * health / (maxHealth + absorb));
            int emptyLength = (int) (innerLength * (maxHealth - health) / (maxHealth + absorb));
            int absorbLength = innerLength - healthLength - emptyLength;
            if (absorb <= 0.0F) {
                healthLength += absorbLength;
                absorbLength = 0;
            }
            healthLength += innerLength - emptyLength - absorbLength - healthLength;
            drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, healthColor, flip);
            if (healthIncrement > 0 && health < maxHealth) {
                int incrementLength;
                if (health + healthIncrement >= maxHealth) {
                    incrementLength = emptyLength;
                } else {
                    incrementLength = (int) (innerLength * healthIncrement / (maxHealth + absorb));
                }
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
                drawFillFlipConcat(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, incrementLength, healthColor, flip);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
            //draw absorption
            if (absorbLength > 0) {
                if (flip) {
                    drawFillFlip(poseStack, left + 1, top + 1, right - 1 - healthLength, bottom - 1, absorbLength, AsteorBar.config.absorptionColor(), true);
                } else {
                    drawFillFlip(poseStack, left + 1 + healthLength, top + 1, right - 1, bottom - 1, absorbLength, AsteorBar.config.absorptionColor(), false);
                }
            }
        } else {
            //draw health
            int healthLength = (int) (innerLength * health / maxHealth);
            if (AsteorBar.config.enableStackHealthBar()) {
                final int unit = AsteorBar.config.fullHealthValue();
                healthLength = (int) (innerLength * (health % unit) / unit);
                int incrementLengthA = 0, incrementLengthB = 0;
                if (healthIncrement > 0 && health < maxHealth) {
                    if ((health % unit) + healthIncrement >= unit) {
                        incrementLengthA = innerLength - healthLength;
                        incrementLengthB = (int) (innerLength * (healthIncrement - (unit - health % unit)) / unit);
                    } else {
                        healthIncrement = Math.min(healthIncrement, maxHealth - health);
                        incrementLengthA = (int) (innerLength * healthIncrement / unit);
                    }
                }
                final var colors = getStackColor((int) (health / unit), incrementLengthB > 0 ? 3 : 2);
                if (colors[0] != 0) drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, innerLength, colors[0], flip);
                if (colors[1] != 0) drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, colors[1], flip);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
                if (incrementLengthA > 0)
                    drawFillFlipConcat(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, incrementLengthA, colors[1], flip);
                if (incrementLengthB > 0)
                    drawFillFlipConcat(poseStack, left + 1, top + 1, right - 1, bottom - 1, 0, incrementLengthB, colors[2], flip);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                if (healthColor != AsteorBar.config.healthColorNormal()) {//might be with poison or wither
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.66F);
                    drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, innerLength, healthColor, flip);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            } else {
                drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, healthColor, flip);
                if (healthIncrement > 0 && health < maxHealth) {
                    int incrementLength;
                    if (health + healthIncrement >= maxHealth) {
                        incrementLength = innerLength - healthLength;
                    } else {
                        incrementLength = (int) (innerLength * healthIncrement / maxHealth);
                    }
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
                    drawFillFlipConcat(poseStack, left + 1, top + 1, right - 1, bottom - 1, healthLength, incrementLength, healthColor, flip);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            //draw absorption
            final var fullAbsorb = AsteorBar.config.enableStackHealthBar() ? AsteorBar.config.fullHealthValue() : maxHealth;
            var displayAbsorb = absorb % fullAbsorb;
            if (displayAbsorb == 0 && absorb > 0) {
                displayAbsorb = fullAbsorb;
            }
            if (i == ABSORPTION_MODE_STACK) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.66F);
                int absorbLength = (int) (innerLength * displayAbsorb / maxHealth);
                drawFillFlip(poseStack, left + 1, top + 1, right - 1, bottom - 1, absorbLength, AsteorBar.config.absorptionColor(), flip);
            } else if (i == ABSORPTION_MODE_BOUND) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.9F);
                int absorbLength = (int) (outerLength * displayAbsorb / maxHealth);
                drawBoundFlip(poseStack, left, top, right, bottom, absorbLength, AsteorBar.config.absorptionColor(), flip);
            }
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (absorb > fullAbsorb && (AsteorBar.config.enableStackHealthBar() || AsteorBar.config.displayAbsorptionDivMaxHealth())) {
                int absorbTimes = (int) (absorb / fullAbsorb);
                if (absorb % fullAbsorb == 0) absorbTimes--;
                if (flip) {
                    Overlays.addStringRender(right, top - 2, 0xFFFF00, "×" + absorbTimes, Overlays.ALIGN_LEFT, true);
                } else {
                    Overlays.addStringRender(left, top - 2, 0xFFFF00, absorbTimes + "×", Overlays.ALIGN_RIGHT, true);
                }
            }
        }
        //draw regeneration
        if (regenerationOffset >= 0) {
            int textureLeft;
            int textureRight;
            if (flip) {
                textureLeft = regenerationOffset - 180;
            } else {
                textureLeft = -regenerationOffset;
            }
            textureRight = textureLeft + right - left - 2;
            RenderSystem.setShaderTexture(0, TEXTURE);
            if (textureRight > 0) {
                drawTextureFill(poseStack, left + 1, top, -textureLeft, 5, 10 + 180 + textureLeft, Y_REGENERATION_FILL);
                drawTextureFill(poseStack, left + 1 - textureLeft, top, textureRight, 5, 10, Y_REGENERATION_FILL);
            } else {
                drawTextureFill(poseStack, left + 1, top, right - left - 2, 5, 10 + 180 + textureLeft, Y_REGENERATION_FILL);
            }
            RenderSystem.setShaderTexture(0, LIGHTMAP_TEXTURE);
        }
        if (AsteorBar.config.displayHealthText()) {
            String hp;
            if (AsteorBar.config.displayAbsorptionTextMethod() == ABSORPTION_TEXT_MODE_TOGETHER && absorb > 0.0F) {
                hp = (Utils.formatNumber(health) + "(+" + Utils.formatNumber(absorb) + ")/" + Utils.formatNumber(maxHealth));
            } else {
                hp = (Utils.formatNumber(health) + "/" + Utils.formatNumber(maxHealth));
            }
            top -= verticalShift;
            Overlays.addStringRender((left + right) / 2, top - 2, 0xFFFFFF, hp, Overlays.ALIGN_CENTER, true);
            if (AsteorBar.config.displayAbsorptionTextMethod() == ABSORPTION_TEXT_MODE_SEPARATE && absorb > 0.0F) {
                if (flip) {
                    Overlays.addStringRender(right - 2, top - 2, 0xFFFF00, Utils.formatNumber(absorb), Overlays.ALIGN_RIGHT, true);
                } else {
                    Overlays.addStringRender(left + 2, top - 2, 0xFFFF00, Utils.formatNumber(absorb), Overlays.ALIGN_LEFT, true);
                }
            }
            top += verticalShift;
        }
        if (highlight) {
            drawBound(poseStack, left, top, right, bottom, AsteorBar.config.healthBoundColorBlink());
        } else if (flashAlpha > 0) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, flashAlpha);
            drawBound(poseStack, left, top, right, bottom, AsteorBar.config.healthBoundColorLow());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        RenderSystem.enableBlend();
        var mc = gui.mc();
        var player = mc.player;
        if (player == null) return;
        float health = player.getHealth();
        boolean highlight = false;
        long tickCount = gui.gui().getGuiTicks();
        if (AsteorBar.config.enableHealthBlink()) {
            highlight = healthBlinkTime > tickCount && (healthBlinkTime - tickCount) / 3L % 2L == 1L;
            if (health < lastHealth && player.invulnerableTime > 0) {
                lastHealthTime = Util.getMillis();
                healthBlinkTime = tickCount + 20L;
            } else if (health > lastHealth && player.invulnerableTime > 0) {
                lastHealthTime = Util.getMillis();
                healthBlinkTime = tickCount + 10L;
            }
            if (Util.getMillis() - lastHealthTime > 1000L) {
                lastHealth = health;
                lastHealthTime = Util.getMillis();
            }
            lastHealth = health;
        }
        float maxHealth = player.getMaxHealth();
        float absorb = player.getAbsorptionAmount();
        int healthType = AsteorBar.config.healthColorNormal();
        if (player.hasEffect(MobEffects.POISON)) {
            healthType = AsteorBar.config.healthColorPoison();
        } else if (player.hasEffect(MobEffects.WITHER)) {
            healthType = AsteorBar.config.healthColorWither();
        } else if (player.isFullyFrozen()) {
            healthType = AsteorBar.config.healthColorFrozen();
        }
        var flashAlpha = -1F;
        if (health < maxHealth * AsteorBar.config.lowHealthRate() && !highlight) {
            int margin = Math.abs((int) tickCount % 20 - 10);
            flashAlpha = 0.08F * margin;
        }
        var regenerationOffset = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regenerationOffset = (int) (tickCount % 30 * 6);
        }
        double healthIncrement = 0;
        if (AsteorBar.compatibility.appleskin) {
            final var foodValues = AsteorBar.platformAdapter.getAppleSkinFoodValues(player);
            if (foodValues != null) {
                healthIncrement = foodValues.healthIncrement();
            }
            healthIncrement = Math.min(healthIncrement, maxHealth - health);
        }
        int left, top, right;
        boolean flip = false;
        switch (Overlays.style) {
            default -> {
                return;
            }
            case Overlays.STYLE_ABOVE_HOT_BAR_LONG -> {
                top = screenHeight - gui.leftHeight() - 2;
                left = screenWidth / 2 - 91;
                right = left + BOUND_FULL_WIDTH_LONG;
                gui.leftHeight(12);
            }
            case Overlays.STYLE_ABOVE_HOT_BAR_SHORT -> {
                top = screenHeight - gui.leftHeight() + 4;
                left = screenWidth / 2 - 91;
                right = left + BOUND_FULL_WIDTH_SHORT;
                gui.leftHeight(6);
            }
            case Overlays.STYLE_TOP_LEFT -> {
                top = Overlays.vertical;
                left = Overlays.horizontal;
                right = left + Overlays.length;
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
                Overlays.vertical += 6;
            }
            case Overlays.STYLE_BOTTOM_RIGHT -> {
                top = screenHeight - Overlays.vertical;
                left = screenWidth - Overlays.length - Overlays.horizontal;
                right = left + Overlays.length;
                flip = true;
                Overlays.vertical += 6;
            }
        }
        draw(poseStack, left, top, right, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, healthIncrement, tickCount, flip);
        RenderSystem.disableBlend();
    }
}
