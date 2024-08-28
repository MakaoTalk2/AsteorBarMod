package com.afoxxvi.asteorbar.mixin;

import com.afoxxvi.asteorbar.overlay.FabricGuiRegistry;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Invoker("renderPlayerHealth")
    public abstract void renderPlayerHealthRaw(GuiGraphics guiGraphics);

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderPlayerHealth(Lnet/minecraft/client/gui/GuiGraphics;)V"), method = "render")
    public void renderPlayerHealth(Gui instance, GuiGraphics guiGraphics) {
        Overlays.reset();
        renderPlayerHealthRaw(guiGraphics);
        FabricGuiRegistry.startRender(instance, guiGraphics);

    }

    @Inject(at = @At(value = "HEAD"), method = "renderHearts", cancellable = true)
    public void renderHearts(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE) {
            ci.cancel();
        }
    }
}

