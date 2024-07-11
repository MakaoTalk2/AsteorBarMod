package com.afoxxvi.asteorbar.utils;

import com.afoxxvi.asteorbar.AsteorBarForge;
import com.afoxxvi.asteorbar.entity.AsteorBarRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

public class ForgePlatformAdapter implements PlatformAdapter {
    @Override
    public boolean isBoss(LivingEntity livingEntity) {
        return livingEntity.getType().is(Tags.EntityTypes.BOSSES);
    }

    @Override
    public boolean isEyeInFluid(Player player) {
        return player.isEyeInFluidType(ForgeMod.WATER_TYPE.get());
    }

    @Override
    public RenderType getRenderType() {
        return AsteorBarRenderType.RENDER_TYPE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Logger getLogger() {
        return AsteorBarForge.LOGGER;
    }

    @Override
    public AppleSkinFoodValues getAppleSkinFoodValues(Player player) {
        return null;
    }
}
