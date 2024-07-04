package com.afoxxvi.asteorbar.overlay;

import com.afoxxvi.asteorbar.overlay.parts.BaseOverlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public class FabricGuiRegistry {
    public static final List<BaseOverlay> REGISTRY = new ArrayList<>();

    static {
        REGISTRY.add(Overlays.PLAYER_HEALTH);
        REGISTRY.add(Overlays.FOOD_LEVEL);
        REGISTRY.add(Overlays.MOUNT_HEALTH);
        REGISTRY.add(Overlays.AIR_LEVEL);
        REGISTRY.add(Overlays.EXPERIENCE_BAR);
        REGISTRY.add(Overlays.ARMOR_LEVEL);
        REGISTRY.add(Overlays.STRING);

    }

    public static void startRender(Gui instance, PoseStack poseStack) {
        var mc = Minecraft.getInstance();
        var tick = 0f;
        var width = mc.getWindow().getGuiScaledWidth();
        var height = mc.getWindow().getGuiScaledHeight();
        var gui = new FabricRenderGui(instance);
        REGISTRY.forEach(baseOverlay -> baseOverlay.render(gui, poseStack, tick, width, height));
    }
}
