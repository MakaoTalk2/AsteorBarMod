package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.client.gui.GuiManaHUD;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import net.minecraft.world.entity.player.Player;

public class ArsNouveauOverlay extends SimpleBarOverlay {
    @Override
    protected Parameters getParameters(Player player) {
        IManaCap mana = CapabilityRegistry.getMana(player).orElse(null);
        if (mana == null || mana.getMaxMana() <= 0) {
            return null;
        }
        Parameters parameters = new Parameters();
        parameters.value = mana.getCurrentMana();
        parameters.capacity = mana.getMaxMana();
        parameters.emptyColor = 0xff3e1853;
        parameters.fillColor = 0xff9d2dfc;
        parameters.fillColor2 = 0xff8226d1;
        parameters.boundColor = 0xfffad64a;
        parameters.boundColor2 = 0xffb26411;
        return parameters;
    }

    private Object guiManaHUD = null;

    private boolean shouldDraw() {
        try {
            if (guiManaHUD == null) {
                guiManaHUD = new GuiManaHUD();
            }
            return ((GuiManaHUD) guiManaHUD).shouldDisplayBar();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean shouldRender(Player player) {
        return AsteorBar.compatibility.arsNouveau && AsteorBar.config.hookArsNouveau() && shouldDraw();
    }
}
