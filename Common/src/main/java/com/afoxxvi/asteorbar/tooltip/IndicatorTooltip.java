package com.afoxxvi.asteorbar.tooltip;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.internal.InternalInfo;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class IndicatorTooltip implements TooltipComponent, ClientTooltipComponent {
    private final int size;

    public IndicatorTooltip(int size) {
        this.size = size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public int getWidth(Font font) {
        return size;
    }

    @Override
    public void renderImage(Font font, int i, int j, PoseStack poseStack, ItemRenderer itemRenderer) {
        GuiHelper.drawString(poseStack, ".", i, j, 0xFFFFFF);
        if (AsteorBar.INTERNAL_MOD_VERSION != InternalInfo.mod_version) {
            if (Minecraft.getInstance().options.renderDebug) {
                GuiHelper.drawString(poseStack, "expected=" + InternalInfo.mod_version + ", current=" + AsteorBar.INTERNAL_MOD_VERSION, i, j, 0xFF5555, true);
            }
            GuiHelper.drawSolidColor(poseStack, i, j, i + size, j + size, 0xFFFF0000);
        } else {
            if (Minecraft.getInstance().options.renderDebug) {
                GuiHelper.drawString(poseStack, "expected=" + InternalInfo.resource_pack_version, i, j, 0xFFFFFF, true);
            }
            var rl = new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "textures/item/utils/" + InternalInfo.resource_pack_version + ".png");
            RenderSystem.setShaderTexture(0, rl);
            GuiHelper.drawTexturedRect(poseStack, i, j, i + size, j + size, 0, 0, 16, 16, 16, 16);
        }
    }
}
