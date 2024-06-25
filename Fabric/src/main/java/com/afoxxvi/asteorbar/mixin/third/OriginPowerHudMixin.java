package com.afoxxvi.asteorbar.mixin.third;

import com.afoxxvi.asteorbar.AsteorBar;
import io.github.apace100.apoli.screen.PowerHudRenderer;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowerHudRenderer.class)
public abstract class OriginPowerHudMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V", at = @At("HEAD"), cancellable = true)
    void render(GuiGraphics context, float delta, CallbackInfo ci) {
        if (AsteorBar.config.enableOverlay() && AsteorBar.compatibility.apoli && AsteorBar.config.hookApoli()) {
            ci.cancel();
        }
    }
}
