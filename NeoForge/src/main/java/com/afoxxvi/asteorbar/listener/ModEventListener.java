package com.afoxxvi.asteorbar.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarNeoForge;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.NeoForgeRenderGui;
import com.afoxxvi.asteorbar.overlay.parts.ToughAsNailsOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.afoxxvi.asteorbar.overlay.Overlays.*;

@EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventListener {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        AsteorBarNeoForge.LOGGER.info("Registering Overlays");
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "player_health"), new NeoForgeRenderGui(PLAYER_HEALTH));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "food_level"), new NeoForgeRenderGui(FOOD_LEVEL));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "mount_health"), new NeoForgeRenderGui(MOUNT_HEALTH));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "thirst_level"), new NeoForgeRenderGui(new ToughAsNailsOverlay()));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "experience_bar"), new NeoForgeRenderGui(EXPERIENCE_BAR));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "armor_level"), new NeoForgeRenderGui(ARMOR_LEVEL));
        event.registerBelow(VanillaGuiLayers.AIR_LEVEL, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "air_level"), new NeoForgeRenderGui(AIR_LEVEL));
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(AsteorBar.MOD_ID, "string"), new NeoForgeRenderGui(STRING));
    }

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.TOGGLE_OVERLAY);
        event.register(KeyBinding.TOGGLE_MOB_BAR);
    }
}
