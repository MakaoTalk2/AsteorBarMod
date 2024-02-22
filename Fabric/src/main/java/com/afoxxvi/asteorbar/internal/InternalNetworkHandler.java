package com.afoxxvi.asteorbar.internal;


import com.afoxxvi.asteorbar.AsteorBar;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class InternalNetworkHandler {
    private static final ResourceLocation CHANNEL = new ResourceLocation(AsteorBar.MOD_ID, "internal");

    @Environment(EnvType.CLIENT)
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(CHANNEL, (client, handler, buf, responseSender) -> {
            byte index = buf.readByte();
            switch (index) {
                case 0 -> InternalInfo.decodeActivate(buf);
                case 1 -> InternalInfo.decodeStatus(buf);
                case 2 -> InternalInfo.decodeInfo(buf);
                case 3 -> InternalInfo.decodeHelmet(buf);
                case 4 -> InternalInfo.decodeToggle(buf);
            }
        });
    }

    public static void sendUseSkillPacket(int skillIndex) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(128);
        buf.writeByte(skillIndex);
        ClientPlayNetworking.send(CHANNEL, buf);
    }
}
