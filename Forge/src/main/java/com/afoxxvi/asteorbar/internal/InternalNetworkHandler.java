package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class InternalNetworkHandler {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(AsteorBar.MOD_ID, "internal"), () -> "1.0", s -> true, s -> true);

    public static void init() {
        CHANNEL.registerMessage(0, Object.class, null, InternalInfo::decodeActivate, InternalNetworkHandler::ignoredHandle);
        CHANNEL.registerMessage(1, Object.class, null, InternalInfo::decodeStatus, InternalNetworkHandler::ignoredHandle);
        CHANNEL.registerMessage(2, Object.class, null, InternalInfo::decodeInfo, InternalNetworkHandler::ignoredHandle);
        CHANNEL.registerMessage(3, Object.class, null, InternalInfo::decodeHelmet, InternalNetworkHandler::ignoredHandle);
        CHANNEL.registerMessage(4, Object.class, null, InternalInfo::decodeToggle, InternalNetworkHandler::ignoredHandle);

        CHANNEL.registerMessage(128, UseSkillPacket.class, UseSkillPacket::encode, null, null);
    }

    @SuppressWarnings("unused")
    private static void ignoredHandle(Object packet, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
    }

    public static class UseSkillPacket {
        int index;

        public UseSkillPacket(int index) {
            this.index = index;
        }

        public static void encode(UseSkillPacket packet, FriendlyByteBuf buffer) {
            buffer.writeByte(packet.index);
        }
    }
}
