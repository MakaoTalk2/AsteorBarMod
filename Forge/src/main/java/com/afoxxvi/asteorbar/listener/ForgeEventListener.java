package com.afoxxvi.asteorbar.listener;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.key.KeyBinding;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.tooltip.CenterTextTooltip;
import com.afoxxvi.asteorbar.tooltip.IndicatorTooltip;
import com.afoxxvi.asteorbar.tooltip.SeparatorTooltip;
import com.afoxxvi.asteorbar.tooltip.Tooltips;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = AsteorBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventListener {
    public static final ResourceLocation TOUGH_AS_NAILS_THIRST_LEVEL = new ResourceLocation("toughasnails", "thirst_level");
    public static final ResourceLocation PARCOOL_STAMINA = new ResourceLocation("parcool", "hud.stamina.host");

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isPaused()) {
            AsteorBar.tick++;
            AsteorBar.castSkill = false;
            AsteorBar.rush = false;
        }
    }

    @SubscribeEvent
    public static void disableVanillaOverlays(RenderGuiOverlayEvent.Pre event) {
        if (!AsteorBar.config.enableOverlay()) return;
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlay == VanillaGuiOverlay.VIGNETTE.type()) {
            Overlays.reset();
        }
        if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.type()
                || overlay == VanillaGuiOverlay.FOOD_LEVEL.type()
                || overlay == VanillaGuiOverlay.AIR_LEVEL.type()
                || (AsteorBar.config.overwriteVanillaExperienceBar() && overlay == VanillaGuiOverlay.EXPERIENCE_BAR.type())
                || overlay == VanillaGuiOverlay.MOUNT_HEALTH.type()
                || (AsteorBar.config.overwriteVanillaArmorBar() && overlay == VanillaGuiOverlay.ARMOR_LEVEL.type())
                || AsteorBar.compatibility.toughAsNails && AsteorBar.config.hookToughAsNails() && overlay.id().equals(TOUGH_AS_NAILS_THIRST_LEVEL)
                || AsteorBar.compatibility.parcool && AsteorBar.config.hookParcool() && overlay.id().equals(PARCOOL_STAMINA)
        ) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMouse(ScreenEvent.MouseScrolled.Pre event) {
        Tooltips.addScroll((int) (event.getScrollDelta() * 10));
    }

    @SubscribeEvent
    public static void handleKeyInput(InputEvent.Key event) {
        KeyBinding.handleKeyInput();
    }

    @SubscribeEvent
    public static void onRenderTooltipPre(RenderTooltipEvent.Pre event) {
        int maxWidth = 0;
        int totalHeight = 0;
        for (var component : event.getComponents()) {
            maxWidth = Math.max(maxWidth, component.getWidth(event.getFont()));
            totalHeight += component.getHeight();
        }
        Tooltips.width = maxWidth;
        Tooltips.checkReset(totalHeight);
        if (Minecraft.getInstance().options.renderDebug) {
            int offset = 0;
            for (var str : Tooltips.getDebugStrings()) {
                GuiHelper.drawString(event.getPoseStack(), str, 10, 50 + offset, 0xffffff);
                offset += 10;
            }
            Tooltips.resetDebugStrings();
        }
    }

    @SubscribeEvent
    public static void onRenderTooltipColor(RenderTooltipEvent.Color event) {
        event.setBackground(0xef4a3d57);
        event.setBorderStart(0xefc8d4fd);
        event.setBorderEnd(0xefefc7fd);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (event.isCanceled() || event.getTooltipElements().isEmpty()) {
            return;
        }
        final var first = event.getTooltipElements().get(0).left();
        if (first.isEmpty()) {
            return;
        }
        if (event.getTooltipElements().size() == 1) return;
        event.getTooltipElements().set(0, Either.right(new CenterTextTooltip(first.get())));
        event.getTooltipElements().add(1, Either.right(new SeparatorTooltip(4)));
        for (int i = 0; i < event.getTooltipElements().size(); i++) {
            var txt = event.getTooltipElements().get(i);
            if (txt.left().isEmpty()) continue;
            var text = txt.left().get();
            Tooltips.addDebugString(text.getString());
            if (Objects.equals("<asteor-version-indicator>", text.getString())) {
                event.getTooltipElements().set(i, Either.right(new IndicatorTooltip(10)));
            }
        }
    }
}
