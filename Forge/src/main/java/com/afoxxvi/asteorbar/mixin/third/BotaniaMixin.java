package com.afoxxvi.asteorbar.mixin.third;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.client.gui.HUDHandler;

@Mixin(value = HUDHandler.class, remap = false)
public abstract class BotaniaMixin {
    @Inject(method = "renderManaInvBar(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("HEAD"), cancellable = true)
    private static void renderManaInvBar(GuiGraphics gui, int totalMana, int totalMaxMana, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE && AsteorBar.compatibility.botania && AsteorBar.config.hookBotania()) {
            ci.cancel();
        }
    }
}
