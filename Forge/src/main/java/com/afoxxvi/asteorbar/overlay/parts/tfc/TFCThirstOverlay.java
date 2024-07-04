package com.afoxxvi.asteorbar.overlay.parts.tfc;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.parts.SimpleBarOverlay;
import com.afoxxvi.asteorbar.utils.Utils;
import net.dries007.tfc.common.capabilities.food.TFCFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class TFCThirstOverlay extends SimpleBarOverlay {
    @Override
    protected Parameters getParameters(Player player) {
        FoodData foodData = player.getFoodData();
        if (!(foodData instanceof TFCFoodData data)) {
            return null;
        }
        float thirst = data.getThirst();
        float overheat = data.getThirstContributionFromTemperature(player);
        Parameters parameters = new Parameters();
        parameters.value = thirst;
        parameters.capacity = 100.0F;
        parameters.fillColor = 0xff3860e4;
        parameters.emptyColor = Utils.mixColor(0xff000000, parameters.fillColor, 0.5F);
        parameters.boundColor = Utils.mixColor(0xffa4002b, 0xff2c0089, overheat / 0.4F);
        return parameters;
    }

    @Override
    protected boolean shouldRender(Player player) {
        return AsteorBar.compatibility.tfc && AsteorBar.config.hookTFC();
    }
}
