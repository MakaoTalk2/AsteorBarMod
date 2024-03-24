package com.afoxxvi.asteorbar.tooltip;

import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix4f;

public class CenterTextTooltip implements TooltipComponent, ClientTooltipComponent {
    private final FormattedCharSequence formattedCharSequence;

    public CenterTextTooltip(FormattedText text) {
        this.formattedCharSequence = text instanceof Component ? ((Component) text).getVisualOrderText() : Language.getInstance().getVisualOrder(text);
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getWidth(Font font) {
        return font.width(formattedCharSequence);
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        final var offset = (Tooltips.width - font.width(formattedCharSequence)) / 2;
        //Tooltips.clientTextTooltip.renderText(font, x + offset, y, matrix4f, bufferSource);
        font.drawInBatch(this.formattedCharSequence, (float) x + offset, (float) y, -1, true, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
    }
}
