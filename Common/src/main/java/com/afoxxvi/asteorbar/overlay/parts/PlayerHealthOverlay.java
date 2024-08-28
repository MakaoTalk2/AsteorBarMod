package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffects;


public class PlayerHealthOverlay extends BaseOverlay {
    public static final int ABSORPTION_MODES = 3;
    public static final int ABSORPTION_TEXT_MODES = 2;
    private final int[] shift = new int[]{0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1};
    private long healthBlinkTime = 0;
    private long lastHealthTime;
    private float lastHealth;

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
        drawBound(guiGraphics, left, top, right, bottom, AsteorBar.config.healthBoundColor());
        drawEmptyFill(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, AsteorBar.config.healthEmptyColor());
        final var outerLength = right - left;
        final var innerLength = outerLength - 2;
        float alpha = (float) Math.cos(tick % 40 / 40.0 * Math.PI * 2) * 0.5F + 0.5F;

        float maxLength = Math.max(health + absorb, maxHealth);

        //draw health
        int healthLength = (int) (innerLength * health / maxLength);
        drawFillFlip(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, healthLength, healthColor, flip);
        if (healthIncrement > 0 && health < maxHealth) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            drawFillFlip(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, healthLength, healthColor, flip);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }


        //draw absorption
        if (absorb > 0) {
            int absorbLength = Math.max((int) (innerLength * absorb / maxLength), 1);
            drawFillFlip(guiGraphics, left + 1 + healthLength, top + 1, right - 1, bottom - 1, absorbLength + 1, AsteorBar.config.absorptionColor(), flip);
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
                drawTextureFill(guiGraphics, left + 1, top, -textureLeft, 5, 10 + 180 + textureLeft, Y_REGENERATION_FILL);
                drawTextureFill(guiGraphics, left + 1 - textureLeft, top, textureRight, 5, 10, Y_REGENERATION_FILL);
            } else {
                drawTextureFill(guiGraphics, left + 1, top, right - left - 2, 5, 10 + 180 + textureLeft, Y_REGENERATION_FILL);
            }
            RenderSystem.setShaderTexture(0, LIGHTMAP_TEXTURE);
        }
        if (AsteorBar.config.displayHealthText()) {
            String hp;
            if (absorb > 0.0F) {
                hp = (Utils.formatNumber(health) + "(+" + Utils.formatNumber(absorb) + ")/" + Utils.formatNumber(maxHealth));
            } else {
                hp = (Utils.formatNumber(health) + "/" + Utils.formatNumber(maxHealth));
            }
            top -= verticalShift;
            Overlays.addStringRender((left + right) / 2, top - 2, 0xFFFFFF, hp, Overlays.ALIGN_CENTER, true);
            top += verticalShift;
        }
        if (highlight) {
            drawBound(guiGraphics, left, top, right, bottom, AsteorBar.config.healthBoundColorBlink());
        } else if (flashAlpha > 0) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, flashAlpha);
            drawBound(guiGraphics, left, top, right, bottom, AsteorBar.config.healthBoundColorLow());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void renderOverlay(RenderGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
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

        top = screenHeight - gui.leftHeight() + 4;
        left = screenWidth / 2 - 91;
        right = left + BOUND_FULL_WIDTH_SHORT;
        gui.leftHeight(6);

        draw(guiGraphics, left, top, right, top + 5, highlight, healthType, health, absorb, maxHealth, flashAlpha, regenerationOffset, healthIncrement, tickCount, flip);
        RenderSystem.disableBlend();
    }
}
