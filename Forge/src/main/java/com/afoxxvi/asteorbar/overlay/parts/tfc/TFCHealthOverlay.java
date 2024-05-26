package com.afoxxvi.asteorbar.overlay.parts.tfc;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.parts.SimpleBarOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import net.dries007.tfc.common.capabilities.food.TFCFoodData;
import net.dries007.tfc.config.HealthDisplayStyle;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class TFCHealthOverlay extends SimpleBarOverlay {
    @Override
    protected boolean isLeftSide() {
        return true;
    }

    @Override
    protected Parameters getParameters(Player player) {
        Parameters parameters = new Parameters();
        parameters.value = player.getHealth();
        parameters.capacity = player.getMaxHealth();
        parameters.fillColor = 0xffff414a;
        parameters.emptyColor = Utils.mixColor(0xff000000, parameters.fillColor, 0.5F);
        parameters.boundColor = 0xffa4002b;
        parameters.boundValue = player.getAbsorptionAmount();
        parameters.boundCapacity = player.getMaxHealth();
        parameters.boundFillColor = 0xffffac41;
        final var foodData = player.getFoodData();
        HealthDisplayStyle style = TFCConfig.CLIENT.healthDisplayStyle.get();
        float healthModifier = 1.0F;
        if (foodData instanceof TFCFoodData data) {
            healthModifier = data.getHealthModifier();
        }
        final var playerHasSaturation = player.getFoodData().getSaturationLevel() > 0;
        final var isHurt = player.getHealth() > 0.0F && player.getHealth() < player.getMaxHealth();
        if (playerHasSaturation && isHurt || player.hurtTime > 0 || player.hasEffect(MobEffects.REGENERATION)) {
            parameters.boundColor = 0xffe0e0e0;
        }
        parameters.centerText = style.format((float) (healthModifier * parameters.value), (float) (healthModifier * parameters.capacity));
        parameters.centerColor = 0xffffff;
        if (player.getAbsorptionAmount() > 0) {
            parameters.leftText = Utils.formatNumber(player.getAbsorptionAmount());
            parameters.leftColor = 0xffff00;
        }
        return parameters;
    }

    @Override
    protected boolean shouldRender(Player player) {
        return AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC();
    }

    @Override
    public boolean shouldOverride() {
        return AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC();
    }
}
