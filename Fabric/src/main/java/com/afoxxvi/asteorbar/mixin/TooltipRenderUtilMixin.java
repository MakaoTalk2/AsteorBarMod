package com.afoxxvi.asteorbar.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipRenderUtil.class)
public abstract class TooltipRenderUtilMixin {
    @Shadow
    private static void renderHorizontalLine(GuiGraphics p_282981_, int p_282028_, int p_282141_, int p_281771_, int p_282734_, int p_281979_) {
    }

    @Shadow
    private static void renderRectangle(GuiGraphics p_281392_, int p_282294_, int p_283353_, int p_282640_, int p_281964_, int p_283211_, int p_282349_) {
    }

    @Shadow
    private static void renderVerticalLine(GuiGraphics p_281270_, int p_281928_, int p_281561_, int p_283155_, int p_282552_, int p_282221_) {
    }

    @Shadow
    private static void renderFrameGradient(GuiGraphics p_282000_, int p_282055_, int p_281580_, int p_283284_, int p_282599_, int p_283432_, int p_282907_, int p_283153_) {
    }

    @Shadow
    private static void renderVerticalLineGradient(GuiGraphics p_282478_, int p_282583_, int p_283262_, int p_283161_, int p_283322_, int p_282624_, int p_282756_) {
    }

    @Inject(method = "renderTooltipBackground", at = @At("HEAD"), cancellable = true)
    private static void renderTooltipBackground(GuiGraphics guiGraphics, int i, int j, int k, int l, int m, CallbackInfo ci) {
        int n = i - 3;
        int o = j - 3;
        int p = k + 3 + 3;
        int q = l + 3 + 3;
        renderHorizontalLine(guiGraphics, n, o - 1, p, m, 0xef4a3d57);
        renderHorizontalLine(guiGraphics, n, o + q, p, m, 0xef4a3d57);
        renderRectangle(guiGraphics, n, o, p, q, m, 0xef4a3d57);
        renderVerticalLineGradient(guiGraphics, n - 1, o, q, m, 0xef4a3d57, 0xef4a3d57);
        renderVerticalLineGradient(guiGraphics, n + p, o, q, m, 0xef4a3d57, 0xef4a3d57);
        renderFrameGradient(guiGraphics, n, o + 1, p, q, m, 0xefc8d4fd, 0xefefc7fd);
        ci.cancel();
    }
}
