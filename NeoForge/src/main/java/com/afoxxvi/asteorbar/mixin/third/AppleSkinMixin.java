package com.afoxxvi.asteorbar.mixin.third;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import squeek.appleskin.client.HUDOverlayHandler;

@Mixin(value = HUDOverlayHandler.class, remap = false)
public interface AppleSkinMixin {
    @Accessor("heldFood")
    static HUDOverlayHandler.HeldFoodCache getHeldFood() {
        throw new AssertionError();
    }
}
