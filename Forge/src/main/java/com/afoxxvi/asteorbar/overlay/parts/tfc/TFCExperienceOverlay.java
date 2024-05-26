package com.afoxxvi.asteorbar.overlay.parts.tfc;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.ExperienceBarOverlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.common.entities.TFCFishingHook;
import net.dries007.tfc.config.DisabledExperienceBarStyle;
import net.dries007.tfc.config.TFCConfig;

public class TFCExperienceOverlay extends ExperienceBarOverlay {
    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
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
        super.renderOverlay(gui, poseStack, partialTick, screenWidth, screenHeight);
    }

    @Override
    public boolean shouldOverride() {
        return AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC();
    }
}
