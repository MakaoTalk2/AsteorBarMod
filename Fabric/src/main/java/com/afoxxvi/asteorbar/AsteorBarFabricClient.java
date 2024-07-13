package com.afoxxvi.asteorbar;

import com.afoxxvi.asteorbar.internal.InternalNetworkHandler;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.network.NetworkHandler;
import com.afoxxvi.asteorbar.tooltip.CenterTextTooltip;
import com.afoxxvi.asteorbar.tooltip.IndicatorTooltip;
import com.afoxxvi.asteorbar.tooltip.SeparatorTooltip;
import com.afoxxvi.asteorbar.tooltip.Tooltips;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jetbrains.annotations.Nullable;

public class AsteorBarFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkHandler.init();
        InternalNetworkHandler.init();
        KeyBindingHelper.registerKeyBinding(KeyBinding.TOGGLE_OVERLAY);
        KeyBindingHelper.registerKeyBinding(KeyBinding.TOGGLE_MOB_BAR);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_SKILL_1);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_SKILL_2);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_SKILL_3);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_SKILL_4);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_ACTIVE_SKILL_WHEEL);
        KeyBindingHelper.registerKeyBinding(KeyBinding.CAST_QUICK_SKILL_WHEEL);
        KeyBindingHelper.registerKeyBinding(KeyBinding.RUSH_FORWARD);
        KeyBindingHelper.registerKeyBinding(KeyBinding.RUSH_BACKWARD);
        KeyBindingHelper.registerKeyBinding(KeyBinding.RUSH_LEFT);
        KeyBindingHelper.registerKeyBinding(KeyBinding.RUSH_RIGHT);
        KeyBindingHelper.registerKeyBinding(KeyBinding.RUSH_INSTANT);
        KeyBindingHelper.registerKeyBinding(KeyBinding.SCROLL_TOOLTIP_UP);
        KeyBindingHelper.registerKeyBinding(KeyBinding.SCROLL_TOOLTIP_DOWN);
        KeyBindingHelper.registerKeyBinding(KeyBinding.SCROLL_TOOLTIP_LEFT);
        KeyBindingHelper.registerKeyBinding(KeyBinding.SCROLL_TOOLTIP_RIGHT);
        KeyBindingHelper.registerKeyBinding(KeyBinding.SCROLL_TOOLTIP_RESET);

        TooltipComponentCallback.EVENT.register(new TooltipComponentCallback() {
            @Override
            public @Nullable ClientTooltipComponent getComponent(TooltipComponent data) {
                if (data instanceof CenterTextTooltip centerTextTooltip) {
                    return centerTextTooltip;
                }
                if (data instanceof SeparatorTooltip separatorTooltip) {
                    return separatorTooltip;
                }
                if (data instanceof IndicatorTooltip indicatorTooltip) {
                    return indicatorTooltip;
                }
                return null;
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyBinding.handleKeyInput();
            AsteorBar.tick++;
            AsteorBar.castSkill = false;
            AsteorBar.rush = false;
        });
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> ScreenMouseEvents.afterMouseScroll(screen).register((screen1, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
            Tooltips.addScroll((int) (verticalAmount * 10));
        }));
    }
}
