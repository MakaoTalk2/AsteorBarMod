package com.afoxxvi.asteorbar.utils;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarForge;
import com.afoxxvi.asteorbar.entity.AsteorBarRenderType;
import com.afoxxvi.asteorbar.internal.InternalInfo;
import com.afoxxvi.asteorbar.internal.InternalNetworkHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;

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
    public AppleSkinFoodValues getAppleSkinFoodValues(Player player) {
        if (!AsteorBar.compatibility.appleskin) {
            return null;
        }
        // if not using third adapter, the game will crash if appleskin is not loaded
        return AppleSkinAdapter.getInstance().getAppleSkinFoodValues(player);
    }

    @Override
    public void sendUseSkillPacket(int skillIndex) {
        if (!InternalInfo.activated) return;
        InternalNetworkHandler.INTERNAL.send(new InternalNetworkHandler.UseSkillPacket(skillIndex), PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendRushPacket(int direction) {
        if (!InternalInfo.activated) return;
        InternalNetworkHandler.INTERNAL.send(new InternalNetworkHandler.RushPacket(direction), PacketDistributor.SERVER.noArg());
    }
}
