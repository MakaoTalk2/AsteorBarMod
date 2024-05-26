package com.afoxxvi.asteorbar.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarForge;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.Overlays;
import dev.ghen.thirst.foundation.gui.ThirstBarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import toughasnails.thirst.ThirstOverlayHandler;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventListener {
    public static long tickCount = 0L;
    private static Object mekasuitEnergyLevel;
    private static Object superiorShieldsShield;
    private static Object vampirismBlood;
    private static Object parcoolStamina;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isPaused()) {
            tickCount++;
        }
    }

    @SubscribeEvent
    public static void onRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (!ModEventListener.registerOverlay) ModEventListener.registerOverlays();
        }
    }

    @SubscribeEvent
    public static void disableVanillaOverlays(RenderGameOverlayEvent.PreLayer event) {
        if (!AsteorBar.config.enableOverlay()) return;
        var overlay = event.getOverlay();
        if (overlay == ForgeIngameGui.VIGNETTE_ELEMENT) {
            Overlays.reset();
        }
        if (mekasuitEnergyLevel == null && AsteorBar.compatibility.mekanism && overlay.getClass().getSimpleName().equals("MekaSuitEnergyLevel")) {
            mekasuitEnergyLevel = overlay;
        }
        if (superiorShieldsShield == null && AsteorBar.compatibility.superiorshields && overlay.getClass().getSimpleName().equals("GuiShieldOverlay")) {
            superiorShieldsShield = overlay;
        }
        if (vampirismBlood == null && AsteorBar.compatibility.vampirism && overlay.getClass().getSimpleName().equals("BloodBarOverlay")) {
            vampirismBlood = overlay;
        }
        if (parcoolStamina == null && AsteorBar.compatibility.parcool && overlay.getClass().getSimpleName().equals("StaminaHUDController")) {
            parcoolStamina = overlay;
        }
        if (overlay == ForgeIngameGui.PLAYER_HEALTH_ELEMENT
                || overlay == ForgeIngameGui.FOOD_LEVEL_ELEMENT
                || overlay == ForgeIngameGui.AIR_LEVEL_ELEMENT
                || (AsteorBar.config.overwriteVanillaExperienceBar() && overlay == ForgeIngameGui.EXPERIENCE_BAR_ELEMENT)
                || overlay == ForgeIngameGui.MOUNT_HEALTH_ELEMENT
                || (AsteorBar.config.overwriteVanillaArmorBar() && overlay == ForgeIngameGui.ARMOR_LEVEL_ELEMENT)
                || AsteorBar.compatibility.toughAsNails && AsteorBar.config.hookToughAsNails() && overlay == ThirstOverlayHandler.THIRST_LEVEL_ELEMENT
                || AsteorBar.compatibility.thirst && AsteorBar.config.hookThirstWasTaken() && overlay == ThirstBarRenderer.THIRST_OVERLAY
                || AsteorBar.compatibility.mekanism && AsteorBar.config.hookMekanism() && overlay == mekasuitEnergyLevel
                || AsteorBar.compatibility.parcool && AsteorBar.config.hookParcool() && overlay == parcoolStamina
                || AsteorBar.compatibility.vampirism && AsteorBar.config.hookVampirism() && overlay == vampirismBlood
                || AsteorBar.compatibility.superiorshields && AsteorBar.config.hookSuperiorShields() && overlay == superiorShieldsShield
                || AsteorBar.compatibility.appleskin && AsteorBar.config.hookAppleSkin() && overlay.getClass().getPackageName().equals("squeek.appleskin.client")
                //|| AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC() && (overlay.id().equals(TFC_HEALTH) || overlay.id().equals(TFC_MOUNT_HEALTH) || overlay.id().equals(TFC_FOOD) || overlay.id().equals(TFC_THIRST))
        ) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleKeyInput(InputEvent.KeyInputEvent event) {
        KeyBinding.handleKeyInput();
    }
}
