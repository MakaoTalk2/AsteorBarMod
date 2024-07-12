package com.afoxxvi.asteorbar.tooltip;

import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class SeparatorTooltip implements TooltipComponent, ClientTooltipComponent {
    private final int height;

    public SeparatorTooltip(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth(Font font) {
        return 0;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        // only draw a text, the line will be rendered, I don't know why
        // this dot can visually be hidden by the line
        guiGraphics.flush();
        //GuiHelper.drawString(guiGraphics, ".", x + 1, y - 6, 0x01c8d4fd, false);
        GuiHelper.drawGradientHorizontal(guiGraphics, x + 1, y, x + Tooltips.width - 1, y + 1, 0xffc8d4fd, 0xffefc7fd);
    }
}
