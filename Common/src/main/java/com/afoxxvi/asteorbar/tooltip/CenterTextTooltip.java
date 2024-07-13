package com.afoxxvi.asteorbar.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix4f;

public class CenterTextTooltip implements TooltipComponent, ClientTooltipComponent {
    private final FormattedCharSequence formattedCharSequence;

    public CenterTextTooltip(FormattedText text) {
        this.formattedCharSequence = text instanceof Component component ? component.getVisualOrderText() : Language.getInstance().getVisualOrder(text);
    }

    public CenterTextTooltip(FormattedCharSequence formattedCharSequence) {
        this.formattedCharSequence = formattedCharSequence;
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
        font.drawInBatch(this.formattedCharSequence, (float) x + offset, (float) y, -1, true, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
    }
}
