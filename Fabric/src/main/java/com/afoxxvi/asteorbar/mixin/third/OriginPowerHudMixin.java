package com.afoxxvi.asteorbar.mixin.third;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.parts.OriginsOverlay;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.apace100.apoli.power.HudRendered;
import io.github.apace100.apoli.screen.PowerHudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PowerHudRenderer.class)
public abstract class OriginPowerHudMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", shift = At.Shift.AFTER), cancellable = true)
    void render(PoseStack matrices, float delta, CallbackInfo ci, @Local List<HudRendered> hudPowers) {
        if (AsteorBar.config.enableOverlay() && AsteorBar.compatibility.apoli && AsteorBar.config.hookApoli()) {
            OriginsOverlay.hudPowers = hudPowers;
            ci.cancel();
        }
    }
}
