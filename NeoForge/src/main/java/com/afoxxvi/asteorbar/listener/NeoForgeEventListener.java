package com.afoxxvi.asteorbar.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarNeoForge;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NeoForgeEventListener {
    public static long tickCount = 0L;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!Minecraft.getInstance().isPaused()) {
            tickCount++;
        }
    }

    @SubscribeEvent
    public static void disableVanillaOverlays(RenderGuiLayerEvent.Pre event) {
        if (!AsteorBar.config.enableOverlay()) return;
        ResourceLocation overlay = event.getName();
        if (overlay == VanillaGuiLayers.PLAYER_HEALTH) {
            Overlays.reset();
        }
        if (overlay == VanillaGuiLayers.PLAYER_HEALTH
                || overlay == VanillaGuiLayers.FOOD_LEVEL
                || overlay == VanillaGuiLayers.AIR_LEVEL
                || (AsteorBar.config.overwriteVanillaExperienceBar() && (overlay == VanillaGuiLayers.EXPERIENCE_BAR || overlay == VanillaGuiLayers.EXPERIENCE_LEVEL))
                || overlay == VanillaGuiLayers.VEHICLE_HEALTH
                || (AsteorBar.config.overwriteVanillaArmorBar() && overlay == VanillaGuiLayers.ARMOR_LEVEL)
        ) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleKeyInput(InputEvent.Key event) {
        KeyBinding.handleKeyInput();
    }
}
