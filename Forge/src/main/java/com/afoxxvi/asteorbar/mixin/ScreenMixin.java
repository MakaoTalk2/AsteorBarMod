package com.afoxxvi.asteorbar.mixin;

import com.afoxxvi.asteorbar.tooltip.Tooltips;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
public class ScreenMixin {
    @Redirect(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;positionTooltip(Lnet/minecraft/client/gui/screens/Screen;IIII)Lorg/joml/Vector2ic;"))
    private Vector2ic positionTooltip(ClientTooltipPositioner instance, Screen screen, int x, int y, int i, int j) {
        final var origin = instance.positionTooltip(screen, x, y, i, j);
        Vector2i vector2i = new Vector2i(origin);
        vector2i.y = Tooltips.getRealY(origin.y(), origin.y() + j, screen.height);
        return vector2i;
    }
}
