package com.afoxxvi.asteorbar.mixin;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.FabricGuiRegistry;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
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

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderPlayerHealth(Lnet/minecraft/client/gui/GuiGraphics;)V"), method = "renderHotbarAndDecorations")
    public void renderPlayerHealth(Gui instance, GuiGraphics guiGraphics) {
        Overlays.reset();
        if (Overlays.style == Overlays.STYLE_NONE) {
            renderPlayerHealthRaw(guiGraphics);
            return;
        }
        FabricGuiRegistry.startRender(instance, guiGraphics);
    }

    @Inject(method = "renderVehicleHealth(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("HEAD"), cancellable = true)
    public void injectVehicle(GuiGraphics p_283368_, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceBar(Lnet/minecraft/client/gui/GuiGraphics;I)V", at = @At("HEAD"), cancellable = true)
    public void injectExp(GuiGraphics p_281906_, int p_282731_, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE && AsteorBar.config.overwriteVanillaExperienceBar()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceLevel(Lnet/minecraft/client/gui/GuiGraphics;F)V", at = @At("HEAD"), cancellable = true)
    public void injectExpLevel(GuiGraphics p_335340_, float p_332198_, CallbackInfo ci) {
        if (Overlays.style != Overlays.STYLE_NONE && AsteorBar.config.overwriteVanillaExperienceBar()) {
            ci.cancel();
        }
    }
}

