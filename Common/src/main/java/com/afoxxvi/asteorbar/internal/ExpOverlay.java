package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.parts.SimpleBarOverlay;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class ExpOverlay extends SimpleBarOverlay {
    int level;

    @Override
    protected Parameters getParameters(Player player) {
        Parameters parameters = new Parameters();
        parameters.boundColor = AsteorBar.config.experienceBoundColor();
        parameters.fillColor = AsteorBar.config.experienceColor();
        parameters.emptyColor = AsteorBar.config.experienceEmptyColor();
        parameters.value = InternalInfo.exp;
        parameters.capacity = InternalInfo.expRequire;
        parameters.leftText = String.valueOf(InternalInfo.exp);
        parameters.rightText = String.valueOf(InternalInfo.expRequire);
        parameters.leftColor = 0xffffff;
        parameters.rightColor = 0xffffff;
        level = player.experienceLevel;
        return parameters;
    }

    @Override
    protected void drawDecorations(GuiGraphics guiGraphics, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
        super.drawDecorations(guiGraphics, left, top, right, bottom, parameters, flip);
        int innerWidth = right - left - 2;
        int textureWidth = Math.min(179, Math.max(0, (innerWidth + 5) / 10 - 1) * 10 + 9);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTextureFillColor(guiGraphics, left + 1, top, innerWidth, 5, 10, Y_EXPERIENCE_DECORATION, textureWidth, 5, AsteorBar.config.experienceColor());
        RenderSystem.setShaderTexture(0, LIGHTMAP_TEXTURE);
        int x = (right + left) / 2;
        int y = top - 2;
        Overlays.addStringRender(x, y, 0x80FF20, String.valueOf(level), Overlays.ALIGN_CENTER, false, true, 0);
    }

    @Override
    protected boolean shouldRender(Player player) {
        return InternalInfo.activated;
    }
}
