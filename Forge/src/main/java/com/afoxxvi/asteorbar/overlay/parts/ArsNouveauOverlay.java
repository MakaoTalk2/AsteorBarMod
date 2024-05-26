package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.GuiManaHUD;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ArsNouveauOverlay extends SimpleBarOverlay {
    private int maxMana = 0;

    @Override
    protected Parameters getParameters(Player player) {
        IManaCap mana = CapabilityRegistry.getMana(player).orElse(null);
        if (mana == null || mana.getMaxMana() <= 0) {
            return null;
        }
        maxMana = mana.getMaxMana();
        Parameters parameters = new Parameters();
        parameters.value = mana.getCurrentMana();
        parameters.capacity = mana.getMaxMana() * (1.0 + ClientInfo.reservedOverlayMana);
        parameters.emptyColor = 0xff3e1853;
        parameters.fillColor = 0xff9d2dfc;
        parameters.fillColor2 = 0xff8226d1;
        parameters.boundColor = 0xfffad64a;
        parameters.boundColor2 = 0xffb26411;
        if (ArsNouveauAPI.ENABLE_DEBUG_NUMBERS) {
            parameters.centerText = mana.getCurrentMana() + "  /  " + maxMana;
            parameters.centerColor = 0xffffff;
        }
        return parameters;
    }

    @Override
    protected void drawDecorations(GuiGraphics guiGraphics, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
        final var innerWidth = right - left - 2;
        if (ClientInfo.reservedOverlayMana > 0.0F) {
            int reserveManaLength = (int) (innerWidth * ClientInfo.reservedOverlayMana);
            int offset = innerWidth - reserveManaLength;
            drawFillFlipConcat(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, offset, reserveManaLength, 0xff330561, flip);
        }
        if (ClientInfo.redTicks()) {
            int redManaLength = (int) (innerWidth * Mth.clamp(0.0F, ClientInfo.redOverlayMana / maxMana, 1.0F));
            final var color = 0x00ff0000 + ((int) (ClientInfo.redOverlayTicks / 35.0F * 0xff)) << 24;
            drawFillFlip(guiGraphics, left + 1, top + 1, right - 1, bottom - 1, 0, redManaLength, color, flip);
        }
    }

    @Override
    protected boolean shouldRender(Player player) {
        return AsteorBar.compatibility.arsNouveau && AsteorBar.config.hookArsNouveau() && GuiManaHUD.shouldDisplayBar();
    }
}
