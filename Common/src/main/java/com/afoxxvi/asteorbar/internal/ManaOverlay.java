package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.BaseOverlay;
import com.afoxxvi.asteorbar.overlay.parts.SimpleBarOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ManaOverlay extends SimpleBarOverlay {
    @Override
    protected Parameters getParameters(Player player) {
        Parameters parameters = new Parameters();
        boolean highlight = AsteorBar.tick - InternalInfo.manaGlint < 4;
        int color = 0xff1976d2;
        if (InternalInfo.manaType == 1) {
            color = 0xff5e35b1;
        }
        int dark = Utils.mixColor(0xFF000000, color, 0.5);
        int light = Utils.mixColor(0xFFFFFFFF, color, 0.5);
        int empty = Utils.mixColor(0xFF000000, color, 0.75);
        parameters.boundColor = highlight ? light : dark;
        parameters.fillColor = color;
        parameters.emptyColor = empty;
        parameters.value = InternalInfo.mana;
        parameters.capacity = InternalInfo.manaMax;
        parameters.centerColor = 0xffffff;
        parameters.centerText = String.format("%.0f/%.0f", InternalInfo.mana * 0.1, InternalInfo.manaMax * 0.1);
        return parameters;
    }

    @Override
    protected boolean shouldRender(Player player) {
        return InternalInfo.activated;
    }
}
