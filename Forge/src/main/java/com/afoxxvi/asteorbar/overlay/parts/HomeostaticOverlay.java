package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import homeostatic.common.Hydration;
import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.capabilities.Water;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.util.WaterHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HomeostaticOverlay extends SimpleBarOverlay {
    private int hydrationLevel;
    private float hydrationSaturationLevel;

    @Override
    protected Parameters getParameters(Player player) {
        final var dataOpt = player.getCapability(CapabilityRegistry.WATER_CAPABILITY);
        if (!dataOpt.isPresent()) {
            return null;
        }
        MobEffectInstance effectInstance = player.getEffect(HomeostaticEffects.THIRST);
        ItemStack heldItem = player.getMainHandItem();
        Hydration hydration = WaterHelper.getItemHydration(heldItem);
        Water data = dataOpt.orElse(new Water());
        int waterLevel = data.getWaterLevel();
        float waterSaturationLevel = data.getWaterSaturationLevel();
        if (hydration != null) {
            hydrationLevel = hydration.amount();
            hydrationSaturationLevel = hydration.saturation();
        } else {
            hydrationLevel = 0;
            hydrationSaturationLevel = 0;
        }
        final var parameters = new Parameters();
        parameters.value = waterLevel;
        parameters.capacity = 20;
        parameters.boundValue = waterSaturationLevel;
        parameters.boundCapacity = 20;
        if (effectInstance != null) {
            parameters.emptyColor = 0xff2e2a1c;
            parameters.fillColor = 0xffa29058;
            parameters.boundColor = 0xff5c6613;
            parameters.boundFillColor = 0xffdacac1;
        } else {
            parameters.emptyColor = 0xff292929;
            parameters.fillColor = 0xff005aaf;
            parameters.boundColor = 0xff00278c;
            parameters.boundFillColor = 0xff82dafd;
        }
        return parameters;
    }

    @Override
    protected void drawDecorations(PoseStack poseStack, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
        super.drawDecorations(poseStack, left, top, right, bottom, parameters, flip);
        if (hydrationLevel == 0 && hydrationSaturationLevel == 0) {
            return;
        }
        float alpha = (float) Math.cos(tick % 40 / 40.0 * Math.PI * 2) * 0.5F + 0.5F;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        double fillWidth = (right - left - 2) * parameters.value / parameters.capacity;
        drawFillFlipConcat(poseStack, left + 1, top + 1, right - 1, bottom - 1, (int) fillWidth, (int) ((right - left - 2) * hydrationLevel / parameters.capacity), parameters.fillColor, flip);
        double boundWidth = (right - left) * parameters.boundValue / parameters.boundCapacity;
        drawBoundFlipConcat(poseStack, left, top, right, bottom, (int) boundWidth, (int) ((right - left) * hydrationSaturationLevel / parameters.boundCapacity), parameters.boundFillColor, flip);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected boolean shouldRender(Player player) {
        return Overlays.homeostatic && AsteorBar.config.hookHomeostatic();
    }
}
