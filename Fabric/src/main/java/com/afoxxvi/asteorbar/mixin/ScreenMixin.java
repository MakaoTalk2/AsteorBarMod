package com.afoxxvi.asteorbar.mixin;

import com.afoxxvi.asteorbar.tooltip.CenterTextTooltip;
import com.afoxxvi.asteorbar.tooltip.IndicatorTooltip;
import com.afoxxvi.asteorbar.tooltip.SeparatorTooltip;
import com.afoxxvi.asteorbar.tooltip.Tooltips;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(GuiGraphics.class)
public abstract class ScreenMixin {
    @Shadow
    protected abstract void renderTooltipInternal(Font p_282675_, List<ClientTooltipComponent> p_282615_, int p_283230_, int p_283417_, ClientTooltipPositioner p_282442_);

    @Redirect(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;positionTooltip(IIIIII)Lorg/joml/Vector2ic;"))
    private Vector2ic positionTooltip(ClientTooltipPositioner instance, int guiWidth, int guiHeight, int preEventX, int preEventY, int i, int j) {
        final var origin = instance.positionTooltip(guiWidth, guiHeight, preEventX, preEventY, i, j);
        Vector2i vector2i = new Vector2i(origin);
        vector2i.y = Tooltips.getRealY(origin.y(), origin.y() + j, guiHeight);
        return vector2i;
    }

    @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", at = @At("HEAD"), cancellable = true)
    private void renderTooltip(Font font, List<Component> list, Optional<TooltipComponent> optional, int i, int j, CallbackInfo ci) {
        if (list.isEmpty()) {
            ci.cancel();
            return;
        }
        List<ClientTooltipComponent> list2 = new ArrayList<>();
        for (int k = 0; k < list.size(); k++) {
            Component component = list.get(k);
            if (k == 0) {
                list2.add(new CenterTextTooltip(component));
                list2.add(new SeparatorTooltip(4));
            } else {
                if (component.getString().equals("<asteor-version-indicator>")) {
                    list2.add(new IndicatorTooltip(10));
                    continue;
                }
                list2.add(ClientTooltipComponent.create(component.getVisualOrderText()));
            }
        }
        optional.ifPresent(tooltipComponent -> list2.add(2, ClientTooltipComponent.create(tooltipComponent)));
        this.renderTooltipInternal(font, list2, i, j, DefaultTooltipPositioner.INSTANCE);
        ci.cancel();
    }

    @Inject(method = "renderTooltipInternal", at = @At("HEAD"))
    private void onPreRenderTooltipInternal(Font font, List<ClientTooltipComponent> list, int i, int j, ClientTooltipPositioner clientTooltipPositioner, CallbackInfo ci) {
        int maxWidth = 0;
        int totalHeight = 0;
        for (var component : list) {
            maxWidth = Math.max(maxWidth, component.getWidth(font));
            totalHeight += component.getHeight();
        }
        Tooltips.width = maxWidth;
        Tooltips.checkReset(totalHeight);
    }
}
