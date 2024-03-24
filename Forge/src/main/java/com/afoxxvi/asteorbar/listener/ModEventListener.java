package com.afoxxvi.asteorbar.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarForge;
import com.afoxxvi.asteorbar.internal.EnergyOverlay;
import com.afoxxvi.asteorbar.internal.ManaOverlay;
import com.afoxxvi.asteorbar.internal.SkillOverlay;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.ForgeRenderGui;
import com.afoxxvi.asteorbar.overlay.parts.MainOverlay;
import com.afoxxvi.asteorbar.overlay.parts.ToughAsNailsOverlay;
import com.afoxxvi.asteorbar.tooltip.CenterTextTooltip;
import com.afoxxvi.asteorbar.tooltip.IndicatorTooltip;
import com.afoxxvi.asteorbar.tooltip.SeparatorTooltip;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventListener {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        AsteorBarForge.LOGGER.info("Registering Overlays");
        event.registerBelow(VanillaGuiOverlay.PLAYER_HEALTH.id(), "skill", new ForgeRenderGui(new SkillOverlay()));
        event.registerBelow(VanillaGuiOverlay.PLAYER_HEALTH.id(), "main", new ForgeRenderGui(new MainOverlay()));
    }

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.TOGGLE_OVERLAY);
        event.register(KeyBinding.TOGGLE_MOB_BAR);
        event.register(KeyBinding.CAST_SKILL_1);
        event.register(KeyBinding.CAST_SKILL_2);
        event.register(KeyBinding.CAST_SKILL_3);
        event.register(KeyBinding.CAST_SKILL_4);
        event.register(KeyBinding.CAST_ACTIVE_SKILL_WHEEL);
        event.register(KeyBinding.CAST_QUICK_SKILL_WHEEL);
        event.register(KeyBinding.RUSH_FORWARD);
        event.register(KeyBinding.RUSH_BACKWARD);
        event.register(KeyBinding.RUSH_LEFT);
        event.register(KeyBinding.RUSH_RIGHT);
        event.register(KeyBinding.RUSH_INSTANT);
        event.register(KeyBinding.SCROLL_TOOLTIP_UP);
        event.register(KeyBinding.SCROLL_TOOLTIP_DOWN);
        event.register(KeyBinding.SCROLL_TOOLTIP_LEFT);
        event.register(KeyBinding.SCROLL_TOOLTIP_RIGHT);
        event.register(KeyBinding.SCROLL_TOOLTIP_RESET);
    }

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SeparatorTooltip.class, x -> x);
        event.register(CenterTextTooltip.class, x -> x);
        event.register(IndicatorTooltip.class, x -> x);
    }
}
