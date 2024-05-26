package com.afoxxvi.asteorbar.overlay.parts.tfc;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.ExperienceBarOverlay;
import net.dries007.tfc.common.entities.misc.TFCFishingHook;
import net.dries007.tfc.config.DisabledExperienceBarStyle;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.client.gui.GuiGraphics;

public class TFCExperienceOverlay extends ExperienceBarOverlay {
    @Override
    public void renderOverlay(RenderGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        var mc = gui.mc();
        var player = mc.player;
        if (player == null) {
            return;
        }
        var fishHook = player.fishing;
        if (fishHook instanceof TFCFishingHook) {
            boolean isStyleLeftHotbar = TFCConfig.CLIENT.disabledExperienceBarStyle.get() == DisabledExperienceBarStyle.LEFT_HOTBAR;
            if (!isStyleLeftHotbar) {
                return;
            }
        }
        super.renderOverlay(gui, guiGraphics, partialTick, screenWidth, screenHeight);
    }

    @Override
    public boolean shouldOverride() {
        return AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC();
    }
}
