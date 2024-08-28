package com.afoxxvi.asteorbar.mixin;

import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


//We don't use this
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Inject(at = @At(value = "HEAD"), method = "blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", cancellable = true)
    public void blitSprite(ResourceLocation p_300860_, int p_298718_, int p_298541_, int p_300996_, int p_298426_, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE) {
            String path = p_300860_.getPath();
            if (path.equals("hud/armor_empty") || path.equals("hud/armor_half") || path.equals("hud/armor_full")) ci.cancel();
        }
    }
}


