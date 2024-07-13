package com.afoxxvi.asteorbar.overlay;

import com.afoxxvi.asteorbar.internal.SkillOverlay;
import com.afoxxvi.asteorbar.overlay.parts.MainOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

public class FabricGuiRegistry {
    private static final SkillOverlay SKILL_OVERLAY = new SkillOverlay();
    private static final MainOverlay MAIN_OVERLAY = new MainOverlay();
    public static void startRender(Gui instance, GuiGraphics guiGraphics) {
        var mc = Minecraft.getInstance();
        var tick = 0f;
        var width = mc.getWindow().getGuiScaledWidth();
        var height = mc.getWindow().getGuiScaledHeight();
        var gui = new FabricRenderGui(instance);
        SKILL_OVERLAY.renderOverlay(gui, guiGraphics, tick, width, height);
        MAIN_OVERLAY.renderOverlay(gui, guiGraphics, tick, width, height);
    }
}
