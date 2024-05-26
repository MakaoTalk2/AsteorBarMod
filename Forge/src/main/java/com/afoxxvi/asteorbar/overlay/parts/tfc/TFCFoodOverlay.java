package com.afoxxvi.asteorbar.overlay.parts.tfc;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.parts.SimpleBarOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import net.dries007.tfc.common.capabilities.food.TFCFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class TFCFoodOverlay extends SimpleBarOverlay {
    @Override
    protected Parameters getParameters(Player player) {
        FoodData foodData = player.getFoodData();
        Parameters parameters = new Parameters();
        parameters.value = foodData.getFoodLevel();
        parameters.capacity = 20.0F;
        parameters.fillColor = 0xff40a735;
        parameters.emptyColor = Utils.mixColor(0xff000000, parameters.fillColor, 0.5F);
        parameters.boundColor = 0xff028723;
        parameters.boundValue = foodData.getSaturationLevel();
        parameters.boundCapacity = 20.0F;
        parameters.boundFillColor = 0xff40a735;
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
